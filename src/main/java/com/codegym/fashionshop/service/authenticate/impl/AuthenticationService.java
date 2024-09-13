package com.codegym.fashionshop.service.authenticate.impl;

import com.codegym.fashionshop.dto.UserInforUserDetails;
import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.UpdatePasswordRequest;
import com.codegym.fashionshop.dto.request.UpdateUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.permission.AppUser;
import com.codegym.fashionshop.repository.authenticate.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user authentication and registration.
 * This class provides methods for user registration, authentication, and token management.
 * <p>
 * Author: KhangDV
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param request The authentication request containing login credentials.
     * @return An {@link AuthenticationResponse} containing the JWT token and authentication status.
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByUsername(request.getUsername());
            UserInforUserDetails userDetails = new UserInforUserDetails(user, user.getRoles());
            var jwtToken = jwtService.generateToken(userDetails);
            var refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .token(jwtToken)
                    .refreshToken(refreshToken.getToken())
                    .avatar(user.getAvatar())
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .message("Đăng nhập thành công!!!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .message("Đăng nhập thất bại")
                    .statusCode(500).build();
        }
    }

    /**
     * Retrieves the information of the authenticated user.
     *
     * @param username The username of the authenticated user.
     * @return An {@link AuthenticationResponse} containing user information.
     */
    public AuthenticationResponse getMyInfo(String username) {
        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .message("Thành công!")
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .userCode(user.getUserCode())
                    .dateCreate(user.getDateCreate())
                    .dateOfBirth(user.getDateOfBirth())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .roles(user.getRoles())
                    .fullName(user.getFullName())
                    .gender(user.getGender())
                    .backgroundImage(user.getBackgroundImage())
                    .avatar(user.getAvatar())
                    .address(user.getAddress())
                    .build();
        } else {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();
        }
    }

    /**
     * Updates the password of the authenticated user.
     *
     * @param updatePasswordRequest The user request containing the updated password details.
     * @return An {@link AuthenticationResponse} indicating the status of the password update operation.
     */
    public AuthenticationResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        String username = updatePasswordRequest.getUsername();
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();
        }
        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getEncryptedPassword())) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Vui lòng nhập đúng mật khẩu!").build();
        }
        if (updatePasswordRequest.getNewPassword() == null && updatePasswordRequest.getNewPassword().isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Mật khẩu mới không được để trống")
                    .build();

        }
        if (!updatePasswordRequest.getConfirmPassword().equals(updatePasswordRequest.getNewPassword())) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Mật khẩu không trùng khớp!").build();
        }
        try {
            user.setEncryptedPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

            userRepository.save(user);

        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(401)
                    .message("Cập nhật mật khẩu thất bại")
                    .error(e.getMessage())
                    .build();
        }
        UserInforUserDetails userDetails = new UserInforUserDetails(user, user.getRoles());
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật mật khẩu thành công!")
                .userId(user.getUserId())
                .username(user.getUsername())
                .userCode(user.getUserCode())
                .dateCreate(user.getDateCreate())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .backgroundImage(user.getBackgroundImage())
                .avatar(user.getAvatar())
                .address(user.getAddress())
                .token(jwtToken)
                .build();
    }

    /**
     * Updates avatar and background image of the authenticated user.
     *
     * @param updateUserRequest The user request containing the updated password details.
     * @return An {@link AuthenticationResponse} indicating the status of the password update operation.
     */
    public AuthenticationResponse updateAvatarAndBackgroundImage(UpdateUserRequest updateUserRequest) {
        String username = updateUserRequest.getUsername();
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();
        }
        if (updateUserRequest.getBackgroundImage() == null && updateUserRequest.getAvatar() == null ) {
             return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Cập nhật hình ảnh không thành công!").build();
        }
        if (updateUserRequest.getAvatar() == null) {
            updateUserRequest.setAvatar(user.getAvatar());
        }
        if (updateUserRequest.getBackgroundImage() == null) {
            updateUserRequest.setBackgroundImage(user.getBackgroundImage());
        }
        try {
            Long userId = user.getUserId();
            String avatar = updateUserRequest.getAvatar();
            String backgroundImage = updateUserRequest.getBackgroundImage();
            userRepository.updateAvatarAndBackGroundImage(backgroundImage, avatar, userId);
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(401)
                    .message("Cập nhật hình ảnh thất bại")
                    .error(e.getMessage())
                    .build();
        }
        user.setAvatar(updateUserRequest.getAvatar());
        user.setBackgroundImage(updateUserRequest.getBackgroundImage());
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật hình ảnh thành công!")
                .userId(user.getUserId())
                .username(user.getUsername())
                .userCode(user.getUserCode())
                .dateCreate(user.getDateCreate())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .backgroundImage(user.getBackgroundImage())
                .avatar(user.getAvatar())
                .address(user.getAddress())
                .build();
    }
}
