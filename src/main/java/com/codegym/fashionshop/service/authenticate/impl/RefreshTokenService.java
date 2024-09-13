package com.codegym.fashionshop.service.authenticate.impl;

import com.codegym.fashionshop.entities.permission.RefreshToken;
import com.codegym.fashionshop.repository.authenticate.IUserRepository;
import com.codegym.fashionshop.repository.authenticate.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private IUserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1800000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void updateRefreshToken(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public boolean checkAndDeleteExpiredToken(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            return true;
        }
        return false;
    }

    public void removeLastRefreshTokenByUserId(Long userId){
        refreshTokenRepository.deleteLastTokenByUserId(userId);
    }

    public void removeRefreshTokenByToken(String token){
        refreshTokenRepository.deleteTokenByRefreshToken(token);
    }

}