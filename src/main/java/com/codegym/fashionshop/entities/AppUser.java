package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity class representing a user in the application.
 * Each user has a unique user ID and contains various personal and authentication details.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "user_name"),
                @UniqueConstraint(name = "APP_USER_CODE_UK", columnNames = "user_code")})
public class AppUser implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * The username used for authentication.
     */
    @Column(name = "user_name", length = 36)
    private String username;

    /**
     * The encrypted password for authentication.
     */
    @Column(name = "encrypted_password", length = 128)
    private String encryptedPassword;

    @Column(name = "user_code", unique = true)
    private String userCode;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @Column(name = "background_image")
    private String backgroundImage;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private AppRole role;

    /**
     * Indicates if the user account is non-expired.
     */
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    /**
     * Indicates if the user account is non-locked.
     */
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    /**
     * Indicates if the user credentials are non-expired.
     */
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    /**
     * Indicates if the user account is enabled.
     */
    @Column(name = "enabled")
    private Boolean enabled;

}
