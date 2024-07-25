package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_ROLE_UK", columnNames = "role_name") })
public class AppRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * The name of the role.
     * Examples: ROLE_ADMIN, ROLE_STORE_MANAGER, ROLE_WAREHOUSE_MANAGER, ROLE_SALES
     */
    @Column(name = "role_name", length = 30, nullable = false)
    private String roleName;

}