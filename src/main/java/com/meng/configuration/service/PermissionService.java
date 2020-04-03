package com.meng.configuration.service;

import com.meng.configuration.entity.security.Permission;

import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/3/27--22:49
 */
public interface PermissionService {
    List<Permission> selectByUserId(Long id);
}
