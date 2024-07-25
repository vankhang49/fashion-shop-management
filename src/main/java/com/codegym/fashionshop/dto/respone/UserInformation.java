package com.codegym.fashionshop.dto.respone;

import com.codegym.fashionshop.entities.AppRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO class for holding user information.
 * This class represents detailed information about a user, including personal details and role.
 * </p>
 *
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {
    private String username;

    private LocalDate dateCreate;

    private String avatar;

    private String fullName;

    private String userCode;

    private Integer gender;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String email;

    private String address;

    private AppRole role;
}
