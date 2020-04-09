package com.meng.configuration.service;


import com.meng.configuration.entity.Permission;

import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/4/3--21:29
 */
public interface PermissionService {

    List<Permission> selectByUserId(Integer id);
}
