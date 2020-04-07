package com.meng.configuration.controller;

import com.meng.configuration.entity.User;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录注册
 *
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 15:20
 */
@Slf4j
@Controller
@RequestMapping()
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("登录")
    @ResponseBody
    @PostMapping("/login-in")
    public ResponseModel loginIn(@RequestBody User user, Model model, HttpServletResponse response) {
        String username = user.getUsername();
        String password = user.getPassword();

        log.info("[loginIn],username={},password={}", username, password);
        ResponseModel responseModel = userService.loginIn(username, password);
        if (responseModel.isSuccess() && responseModel.getData() != null) {
            String token = (String) responseModel.getData();
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            Cookie cookie = new Cookie(tokenHeader, tokenHead+token);
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
            return ResponseModel.SUCCESS(tokenMap);
        } else {
            model.addAttribute("msg", "测试");
            model.addAttribute("responseModel", responseModel);
            return ResponseModel.ERROR("账号或密码错误");
        }
    }


    @ApiOperation("注册")
    @PostMapping("/register")
    public ResponseModel register(String usernumber, String password) {
        log.info("[register]");
//        return userService.register(usernumber,password);
        return ResponseModel.SUCCESS();
    }

    @ApiOperation("退出登录")
    @PostMapping("/login-out")
    public void loginOut() {

    }
}
