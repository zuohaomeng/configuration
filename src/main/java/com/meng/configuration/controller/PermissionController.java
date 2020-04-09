package com.meng.configuration.controller;

import com.meng.configuration.entity.vo.UserAddVO;
import com.meng.configuration.entity.vo.UserRoleVO;
import com.meng.configuration.service.RoleService;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 权限管理
 *
 * @author HAO.ZUO
 * @date 2020/3/27--22:48
 */
@Slf4j
@Controller
@RequestMapping("/permission")
@PreAuthorize("hasRole('permission')")
public class PermissionController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @RequestMapping("to-add")
    public String toAdd() {
        return "permission/userAdd";
    }

    @RequestMapping("to-permissionManage")
    public String toPermissionManage() {
        return "permission/permissionManage";
    }

    /**
     * 添加用户
     *
     * @param UserAddVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResponseModel add(@RequestBody UserAddVO UserAddVO) {
        return userService.register(UserAddVO);
    }

    /**
     * 获取所用用户角色关系
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/role-relation-list")
    public HashMap getRoleRelations(int page, int limit) {
        List<UserRoleVO> roleRelations = roleService.getRoleRelations(page, limit);
        Integer count = userService.getCount();
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", count);
        map.put("data", roleRelations);
        return map;
    }

    @ResponseBody
    @GetMapping("/change-role")
    public ResponseModel changeRole() {


        return ResponseModel.SUCCESS();
    }
}
