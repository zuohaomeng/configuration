package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meng.configuration.component.RoleConstant;
import com.meng.configuration.entity.User;
import com.meng.configuration.entity.vo.UserRoleVO;
import com.meng.configuration.mapper.RoleMapper;
import com.meng.configuration.service.RoleService;
import com.meng.configuration.service.UserService;
import javafx.scene.control.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理相关
 * @author HAO.ZUO
 * @date 2020/4/8--19:49
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserService userService;

    @Override
    public int addRoleRelation(int roleId, int userId) {
        return roleMapper.addRoleRelation(roleId, userId);
    }

    @Override
    public int getRoleByUserId(Integer userId) {
        int roleid = roleMapper.getRoleByUserId(userId);
        return roleid;
    }

    @Override
    public List<UserRoleVO> getRoleRelations(int page, int limit) {
        Page page1 = new Page(page, limit);
        List<UserRoleVO> roleRelations = roleMapper.getRoleRelations(page1);
        for (int i = 0; i < roleRelations.size(); i++) {
            UserRoleVO userRoleVO = roleRelations.get(i);
            if(userRoleVO.getRoleId().equals(RoleConstant.ADMIN.getRoleId())){
                userRoleVO.setRoleName(RoleConstant.ADMIN.getRoleName());
            }else if(userRoleVO.getRoleId().equals(RoleConstant.POWERUSER.getRoleId())){
                userRoleVO.setRoleName(RoleConstant.POWERUSER.getRoleName());
            }else if(userRoleVO.getRoleId().equals(RoleConstant.USER.getRoleId())){
                userRoleVO.setRoleName(RoleConstant.USER.getRoleName());
            }
        }
        return roleRelations;
    }
}
