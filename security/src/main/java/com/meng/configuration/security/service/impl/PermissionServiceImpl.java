package com.meng.configuration.security.service.impl;

import com.meng.configuration.entity.Permission;
import com.meng.configuration.security.mapper.PermissionMapper;
import com.meng.configuration.security.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/4/3--21:29
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> selectByUserId(Long id) {
        return permissionMapper.selectByUserId(id);
    }
}
