package com.codegym.fashionshop.dto;

import com.codegym.fashionshop.entities.permission.AppRole;
import com.codegym.fashionshop.entities.permission.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserInforUserDetails implements UserDetails {
    private AppUser user;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserInforUserDetails(AppUser user, Set<AppRole> roles) {
        this.user = user;
        if (roles != null) {
            for (AppRole role : roles) {
                //ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE, ROLE_CUSTOMER
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(authority);
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}