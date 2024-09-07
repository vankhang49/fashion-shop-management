package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.authenticate.impl.RoleService;
import com.codegym.fashionshop.service.authenticate.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing users and roles.
 * This class provides endpoints for user management including retrieving, creating, updating users and fetching roles.
 * <p>
 * Author: KhangDV
 */
@RestController
@RequestMapping("/api/auth/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Retrieves all users with optional search and pagination.
     *
     * @param page          The page number for pagination (default is 0).
     * @param searchContent The search content to filter users by username, user code, or role name (default is empty).
     * @param codeSort      The field to sort by user code (optional).
     * @param codeDirection The sort direction for user code ('asc' or 'desc').
     * @param nameSort      The field to sort by full name (optional).
     * @param nameDirection The sort direction for full name ('asc' or 'desc').
     * @param roleSort      The field to sort by role ID (optional).
     * @param roleDirection The sort direction for role ID ('asc' or 'desc').
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the list of users.
     * */
    @GetMapping()
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "searchContent", defaultValue = "") String searchContent,
                                         @RequestParam(name = "roleName", defaultValue = "") String roleName,
                                         @RequestParam(name = "codeSort", defaultValue = "") String codeSort,
                                         @RequestParam(name = "codeDirection", defaultValue = "") String codeDirection,
                                         @RequestParam(name = "nameSort", defaultValue = "") String nameSort,
                                         @RequestParam(name = "nameDirection", defaultValue = "") String nameDirection,
                                         @RequestParam(name = "roleSort", defaultValue = "") String roleSort,
                                         @RequestParam(name = "roleDirection", defaultValue = "") String roleDirection) {
        if (page < 0) {
            page = 0;
        }
        // Tạo danh sách các Sort.Order dựa trên các tham số từ RequestParam
        List<Sort.Order> orders = new ArrayList<>();
        if (codeSort != null && !codeSort.isEmpty()) {
            orders.add(createSortOrder("user_code", codeDirection));
        }
        if (nameSort != null && !nameSort.isEmpty()) {
            orders.add(createSortOrder("full_name", nameDirection));
        }
        if (roleSort != null && !roleSort.isEmpty()) {
            orders.add(createSortOrder("role_id", roleDirection));
        }

        // Tạo đối tượng PageRequest với Sort tương ứng
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(orders));
        AuthenticationResponse response = userService.searchAllByFullNameOrUserCodeAndRoleName(searchContent, roleName, pageRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Creates a Sort.Order instance based on the given sort field and direction.
     *
     * @param sortBy       The field to sort by.
     * @param sortDirection The sort direction ('asc' or 'desc').
     * @return A Sort.Order instance representing the sort criteria.
     */
    private Sort.Order createSortOrder(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return new Sort.Order(direction, sortBy);
    }

    /**
     * Retrieves all roles.
     *
     * @return A {@link ResponseEntity} containing the list of roles.
     */
    @GetMapping("/roles")
    public ResponseEntity<List<AppRole>> getAllRoles() {
        List<AppRole> roles = roleService.findAll();
        if (roles.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin chức vụ");
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Retrieves a user for update by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the user details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserUpdate(@PathVariable(name = "id") Long id) {
        AuthenticationResponse response = userService.findUserUpdate(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Creates a new user with the provided details.
     *
     * @param appUserRequest The request containing user details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the status of the creation.
     */
    @PostMapping()
    public ResponseEntity<?> createUser(@Validated @RequestBody AppUserRequest appUserRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse response = userService.save(appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates an existing user with the provided details.
     *
     * @param id             The ID of the user to be updated.
     * @param appUserRequest The request containing updated user details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the status of the update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated @RequestBody AppUserRequest appUserRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse response = userService.updateUser(id, appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        AppUser appUser = userService.findById(id);
        if (appUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return ResponseEntity.status(200).body("Xoá nhân viên thành công!");
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        AppUser appUser = userService.findById(id);
        if (appUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.disableUser(id);
        return ResponseEntity.status(200).body("Khóa tài khoản nhân viên thành công!");
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
        AppUser appUser = userService.findById(id);
        if (appUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.enableUser(id);
        return ResponseEntity.status(200).body("Khôi phục tài khoản nhân viên thành công!");
    }
}
