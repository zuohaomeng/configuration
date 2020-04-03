package com.meng.configuration.service.impl;

import com.meng.configuration.entity.security.Permission;
import com.meng.configuration.mapper.PermissionMapper;
import com.meng.configuration.service.PermissionService;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/3/27--22:49
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
