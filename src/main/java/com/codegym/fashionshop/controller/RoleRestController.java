package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.authenticate.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing roles.
 * This class provides endpoints for role management including retrieving roles.
 * <p>
 * Author: KhangDV
 */
@RestController
@RequestMapping("/api/auth/roles")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    /**
     * Retrieves all roles.
     *
     * @return A {@link ResponseEntity} containing the list of roles.
     */
    @GetMapping
    public ResponseEntity<List<AppRole>> getAllRoles() {
        List<AppRole> roles = roleService.findAll();
        if (roles.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin chức vụ");
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
