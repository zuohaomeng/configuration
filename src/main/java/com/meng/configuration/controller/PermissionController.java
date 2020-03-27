package com.meng.configuration.controller;

import com.meng.configuration.entity.User;
import com.meng.configuration.entity.vo.UserAddVO;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 权限管理
 * @author HAO.ZUO
 * @date 2020/3/27--22:48
 */
@Slf4j
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private UserService userService;

    @RequestMapping("to-add")
    public String toAdd() {
        return "permission/userAdd";
    }

    /**
     * 添加用户
     * @param UserAddVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResponseModel add(@RequestBody UserAddVO UserAddVO) {
        return userService.register(UserAddVO);
    }
}
