package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.UpdatePasswordRequest;
import com.codegym.fashionshop.dto.request.UpdateUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import com.codegym.fashionshop.service.authenticate.impl.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user authentication and profile.
 * This class provides endpoints for user authentication, profile retrieval, and user update.
 * <p>
 * Author: KhangDV
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response
    ){
        AuthenticationResponse authRespone = authenticationService.authentication(request);
        if (authRespone.getStatusCode() == 200) {
            // Thiết lập cookie HTTP-only
            Cookie cookie = new Cookie("token", authRespone.getToken());
            cookie.setHttpOnly(true);
            // cookie.setSecure(true); // Chỉ gửi cookie qua HTTPS trong môi trường sản xuất
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Thời gian tồn tại của cookie (1 ngày)
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(authRespone);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletResponse response){
        // Thiết lập cookie HTTP-only
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // Chỉ gửi cookie qua HTTPS trong môi trường sản xuất
        cookie.setPath("/");
        cookie.setMaxAge(0); // Thời gian tồn tại của cookie (0)
        response.addCookie(cookie);
        return ResponseEntity.status(200).body("Đăng xuất thành công!");
    }

    @GetMapping("/user-role")
    public ResponseEntity<String> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        String roleName = user.getRole().getRoleName();
        return ResponseEntity.status(200).body(roleName);
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     * @throws RuntimeException if an error occurs while retrieving user information.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/get-profile")
    public ResponseEntity<?> getMyProfile(){
        System.out.println("call");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates the information of a user.
     *
     * @param updatePasswordRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @PutMapping("/update-password")
    public ResponseEntity<?> updateUser(@Validated @RequestBody UpdatePasswordRequest updatePasswordRequest
            , BindingResult bindingResult){
        System.out.println("call update");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        AuthenticationResponse response = authenticationService.updatePassword(updatePasswordRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates avatar and background image of a user.
     *
     * @param updateUserRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @PatchMapping("/update-image")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        AuthenticationResponse response = authenticationService.updateAvatarAndBackgroundImage(updateUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
