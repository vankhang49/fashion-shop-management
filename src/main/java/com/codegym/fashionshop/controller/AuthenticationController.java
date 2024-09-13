package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.UpdatePasswordRequest;
import com.codegym.fashionshop.dto.request.UpdateUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.permission.AppRole;
import com.codegym.fashionshop.entities.permission.AppUser;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import com.codegym.fashionshop.service.authenticate.impl.RefreshTokenService;
import com.codegym.fashionshop.service.authenticate.impl.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response
    ){
        AuthenticationResponse authResponse = authenticationService.authentication(request);

        if (authResponse.getStatusCode() == 200) {
            // Thiết lập cookie HTTP-only
            ResponseCookie cookie = ResponseCookie.from("token", authResponse.getToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(1 * 60 * 60)
                    .build(); // Thời gian tồn tại của cookie (0)

            ResponseCookie newRefreshTokenCookie = ResponseCookie.from("rft", authResponse.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(2 * 60 * 60)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
            response.addHeader("Set-Cookie", newRefreshTokenCookie.toString());
        }
        return ResponseEntity.status(authResponse.getStatusCode()).body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(name = "userId") Long userId){
        System.out.println(userId);
        String rft = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rft".equals(cookie.getName())) {
                    rft = cookie.getValue();
                }
            }
        }

        // Thiết lập cookie HTTP-only
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build(); // Thời gian tồn tại của cookie (0)

        ResponseCookie newRefreshTokenCookie = ResponseCookie.from("rft", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        response.addHeader("Set-Cookie", newRefreshTokenCookie.toString());

        refreshTokenService.removeRefreshTokenByToken(rft);

        return ResponseEntity.status(200).body("Đăng xuất thành công!");
    }

    @GetMapping("/user-role")
    public ResponseEntity<?> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        Set<AppRole> roles = user.getRoles();
        return ResponseEntity.status(200).body(roles);
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
