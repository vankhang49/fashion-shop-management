package com.codegym.fashionshop.dto.respone;

import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

/**
 * Response DTO for authentication-related operations.
 * Contains various fields to represent authentication status, user details,
 * and related information in a structured format.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private int statusCode;

    private String error;

    private String message;

    private String token;

    private Long userId;

    private String username;

    private LocalDate dateCreate;

    private String backgroundImage;

    private String avatar;

    private String fullName;

    private String userCode;

    private Integer gender;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String email;

    private String address;

    private AppRole role;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    private Page<AppUser> users;

    private List<AppRole> roles;
}