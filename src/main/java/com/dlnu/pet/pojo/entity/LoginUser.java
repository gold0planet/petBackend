package com.dlnu.pet.pojo.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class LoginUser implements UserDetails {
    private Long userId;
    private String phone;

    public LoginUser(Long userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 如有权限可返回
    }

    @Override
    public String getPassword() {
        return null; // JWT不需要密码
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String getUsername() {
        return phone;
    }
}