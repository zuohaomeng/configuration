package com.meng.configuration.service.impl;

import com.meng.configuration.entity.Permission;
import com.meng.configuration.mapper.PermissionMapper;
import com.meng.configuration.service.PermissionService;
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
    public List<Permission> selectByUserId(Integer id) {
        return permissionMapper.selectByUserId(id);
    }
}
