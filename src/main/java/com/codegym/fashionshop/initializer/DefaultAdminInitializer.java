package com.codegym.fashionshop.initializer;

import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
import com.codegym.fashionshop.service.authenticate.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DefaultAdminInitializer implements CommandLineRunner {

    private final IAppUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;

    public DefaultAdminInitializer(IAppUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Danh sách các vai trò mặc định
        String[] roleNames = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_SALESMAN", "ROLE_WAREHOUSE"};

        // Tạo các vai trò nếu chúng chưa tồn tại
        for (String roleName : roleNames) {
            AppRole role = roleService.findByRoleName(roleName);
            if (role == null) {
                role = new AppRole();
                role.setRoleName(roleName);
                roleService.save(role);
            }
        }

        // Kiểm tra xem đã có admin trong cơ sở dữ liệu chưa
        if (!userService.existsByUsername("admin")) {
            //Tìm kiếm trong DB có Role tên là ROLE_ADMIN không?
            AppRole adminRole = roleService.findByRoleName("ROLE_ADMIN");
            if (adminRole == null) {
                // Nếu chưa có, tạo mới Role ROLE_ADMIN
                adminRole = new AppRole();
                adminRole.setRoleName("ROLE_ADMIN");
                roleService.save(adminRole);
            }
            // Tạo tài khoản admin mặc định
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setEncryptedPassword(passwordEncoder.encode("1234"));
            admin.setRole(adminRole);
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            userService.save(admin);
        }
    }
}
