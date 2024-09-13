package com.codegym.fashionshop.service.authenticate.impl;

import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.permission.AppRole;
import com.codegym.fashionshop.entities.permission.AppUser;
import com.codegym.fashionshop.repository.authenticate.IRoleRepository;
import com.codegym.fashionshop.repository.authenticate.IUserRepository;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service implementation for managing {@link AppUser} entities.
 * This class provides methods for CRUD operations and additional operations related to user management.
 * <p>
 * Author: KhangDV
 */
@Service
@RequiredArgsConstructor
public class UserService implements IAppUserService {
    @Autowired
    private IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves an {@link AppUser} entity by username.
     *
     * @param username The username to search for.
     * @return The found {@link AppUser} entity or null if not found.
     */
    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Searches for {@link AppUser} entities by username, user code, or role name, returning a paginated result.
     *
     * @param searchContent The search content to match against usernames or user codes
     * @param roleName      The search content to match against role names.
     * @param pageable      The pagination information.
     * @return An {@link AuthenticationResponse} containing the status code and a list of matching {@link AppUser} entities.
     */
    @Override
    public AuthenticationResponse searchAllByFullNameOrUserCodeAndRoleName(String searchContent, String roleName, Pageable pageable) {
        Page<AppUser> users = userRepository.searchAppUserByFullNameOrUserCodeAndRoleName(searchContent, roleName, pageable);
        if (users == null || users.getTotalElements() == 0) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Không tìm thấy kết quả!")
                    .build();
        }
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Lấy danh sách nhân viên thành công!")
                .users(users)
                .build();
    }

    /**
     * Retrieves all {@link AppUser} entities.
     *
     * @return A list of all {@link AppUser} entities.
     */
    @Override
    public List<AppUser> findAll() {
        return userRepository.findAllUser();
    }

    /**
     * Saves a new {@link AppUser} entity.
     *
     * @param appUser The {@link AppUser} entity to save.
     */
    @Override
    public void save(AppUser appUser) {
        userRepository.save(appUser);
    }

    /**
     * Saves a new {@link AppUser} entity based on the provided {@link AppUserRequest}.
     * Encrypts the password using the configured {@link PasswordEncoder}.
     *
     * @param appUserRequest The {@link AppUserRequest} containing user details to save.
     * @return An {@link AuthenticationResponse} indicating the status of the save operation.
     */
    @Override
    public AuthenticationResponse save(AppUserRequest appUserRequest) {
        String username = appUserRequest.getUsername();
        if (appUserRequest.getPassword() == null || appUserRequest.getPassword().isEmpty()) {
            appUserRequest.setPassword("123");
        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEncryptedPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        user.setUserCode(appUserRequest.getUserCode());
        user.setDateCreate(LocalDate.now());
        user.setBackgroundImage(appUserRequest.getBackgroundImage());
        user.setAvatar(appUserRequest.getAvatar());
        user.setFullName(appUserRequest.getFullName());
        user.setGender(appUserRequest.getGender());
        user.setDateOfBirth(appUserRequest.getDateOfBirth());
        user.setPhoneNumber(appUserRequest.getPhoneNumber());
        user.setEmail(appUserRequest.getEmail());
        user.setAddress(appUserRequest.getAddress());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setRoles(appUserRequest.getRoles());
        try {
        userRepository.save(user);
        }catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Không thể thêm mới nhân viên, có thể nhân viên đã tồn tại!")
                    .build();
        }
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Thêm mới thành công!\n" + "Tên đăng nhập: " + username + "\n" + "Mật khẩu: " + appUserRequest.getPassword())
                .build();
    }

    /**
     * Updates an existing {@link AppUser} entity based on the provided user ID and {@link AppUserRequest}.
     * Encrypts the password using the configured {@link PasswordEncoder}.
     *
     * @param userId         The ID of the user to update.
     * @param appUserRequest The {@link AppUserRequest} containing updated user details.
     * @return An {@link AuthenticationResponse} indicating the status of the update operation.
     */
    public AuthenticationResponse updateUser(Long userId, AppUserRequest appUserRequest) {
        Optional<AppUser> appUser = userRepository.findUserById(userId);
        if (appUser.isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Không tìm thấy kết quả!")
                    .build();
        }
        AppUser user = appUser.get();
        user.setUsername(appUserRequest.getUsername());
        if (appUserRequest.getPassword() != null || !appUserRequest.getPassword().isEmpty()) {
            user.setEncryptedPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        }
        user.setUserCode(appUserRequest.getUserCode());
        user.setDateCreate(LocalDate.now());
        user.setBackgroundImage(appUserRequest.getBackgroundImage());
        user.setAvatar(appUserRequest.getAvatar());
        user.setFullName(appUserRequest.getFullName());
        user.setGender(appUserRequest.getGender());
        user.setDateOfBirth(appUserRequest.getDateOfBirth());
        user.setPhoneNumber(appUserRequest.getPhoneNumber());
        user.setEmail(appUserRequest.getEmail());
        user.setAddress(appUserRequest.getAddress());
        user.setAccountNonExpired(appUserRequest.getAccountNonExpired());
        user.setAccountNonLocked(appUserRequest.getAccountNonLocked());
        user.setCredentialsNonExpired(appUserRequest.getCredentialsNonExpired());
        user.setEnabled(appUserRequest.getEnabled());
        user.setRoles(appUserRequest.getRoles());
        try {
        userRepository.save(user);
        }catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Không thể cập nhật nhân viên, lỗi hệ thống!")
                    .build();
        }
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật thành công!")
                .build();
    }

    /**
     * Removes a {@link AppUser} entity by ID.
     *
     * @param id The ID of the user to remove.
     */
    @Override
    public void remove(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public void disableUser(Long id) {
        userRepository.disableUser(id);
    }

    @Override
    public void enableUser(Long id) {
        userRepository.enableUser(id);
    }

    /**
     * Retrieves an {@link AppUser} entity by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The found {@link AppUser} entity or null if not found.
     */
    @Override
    public AppUser findById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    /**
     * Finds an existing {@link AppUser} entity by ID and retrieves related roles for update operations.
     *
     * @param id The ID of the user to find.
     * @return An {@link AuthenticationResponse} containing the found user details and related roles.
     */
    public AuthenticationResponse findUserUpdate(Long id){
        Optional<AppUser> appUser = userRepository.findUserById(id);
        if (appUser.isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Không tìm thấy kết quả!")
                    .build();
        }
        AppUser updatedAppUser = appUser.get();
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Đã tìm thấy kết quả!")
                .userId(id)
                .username(updatedAppUser.getUsername())
                .userCode(updatedAppUser.getUserCode())
                .avatar(updatedAppUser.getAvatar())
                .backgroundImage(updatedAppUser.getBackgroundImage())
                .fullName(updatedAppUser.getFullName())
                .gender(updatedAppUser.getGender())
                .dateOfBirth(updatedAppUser.getDateOfBirth())
                .dateCreate(updatedAppUser.getDateCreate())
                .email(updatedAppUser.getEmail())
                .phoneNumber(updatedAppUser.getPhoneNumber())
                .address(updatedAppUser.getAddress())
                .roles(updatedAppUser.getRoles())
                .accountNonExpired(updatedAppUser.getAccountNonExpired())
                .credentialsNonExpired(updatedAppUser.getCredentialsNonExpired())
                .accountNonLocked(updatedAppUser.getAccountNonLocked())
                .enabled(updatedAppUser.getEnabled())
                .build();
    }

    /**
     * Checks if a user exists by username.
     *
     * @param username The username to check.
     * @return true if the user exists, otherwise false.
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
