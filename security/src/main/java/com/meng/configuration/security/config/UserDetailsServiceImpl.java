package com.meng.configuration.security.config;

import com.meng.configuration.entity.Permission;
import com.meng.configuration.security.service.PermissionService;
import com.meng.configuration.security.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO 自定义用户认证与授权
 * @Author: Hao.Zuo
 * @Date: 2020/1/3 16:22
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.meng.configuration.entity.User user = userService.getByUserNumber(userName);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user != null) {
            // 获取用户授权
            List<Permission> tbPermissions = permissionService.selectByUserId(user.getId());

            // 声明用户授权
            tbPermissions.forEach(tbPermission -> {
                if (tbPermission != null && tbPermission.getEnname() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
                    grantedAuthorities.add(grantedAuthority);
                }
            });
        }
        return new User(user.getUserNumber(), user.getPassword(), grantedAuthorities);
    }
}
