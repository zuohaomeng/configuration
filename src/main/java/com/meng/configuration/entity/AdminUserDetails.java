package com.meng.configuration.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * @author HAO.ZUO
 * @date 2020/4/4--23:06
 */
public class AdminUserDetails  implements UserDetails {

    private User user;
    private List<Permission> permissionList;

    public AdminUserDetails(User umsAdmin, List<Permission> permissionList) {
        this.user = umsAdmin;
        this.permissionList = permissionList;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        //返回当前用户的权限
//        return permissionList.stream()
//                .filter(permission -> permission.getValue()!=null)
//                .map(permission ->new SimpleGrantedAuthority(permission.getValue()))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
