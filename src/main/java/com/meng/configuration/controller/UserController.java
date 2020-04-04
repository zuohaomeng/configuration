package com.meng.configuration.controller;

import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 登录注册
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 15:20
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login-in")
    public String loginIn(String username, String password, Model model){
        log.info("[loginIn],username={},password={}",username,password);
        ResponseModel responseModel = userService.loginIn(username, password);
        if(responseModel.isSuccess()){
            return "redirect:/index";
        }else {
            model.addAttribute("msg","测试");
            model.addAttribute("responseModel",responseModel);
            return "login";
        }
    }


    @ApiOperation("注册")
    @PostMapping("/register")
    public ResponseModel register(String usernumber, String password){
        log.info("[register]");
//        return userService.register(usernumber,password);
        return ResponseModel.SUCCESS();
    }
    @ApiOperation("退出登录")
    @PostMapping("/login-out")
    public void loginOut(){

    }
}
