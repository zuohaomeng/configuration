package com.meng.configuration.controller;

import com.meng.configuration.entity.User;
import com.meng.configuration.entity.vo.UserVo;
import com.meng.configuration.register.ZkApi;
import com.meng.configuration.service.RoleService;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 11:44
 */
@Slf4j
@Controller
@RequestMapping
public class IndexController {
    @Autowired
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @GetMapping("/index")
    public String index() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ApiOperation("登录")
    @ResponseBody
    @PostMapping("/login-in")
    public ResponseModel loginIn(@RequestBody User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        String username = user.getUsername();
        String password = user.getPassword();
        log.info("[loginIn],username={},password={}", username, password);

        ResponseModel responseModel = userService.loginIn(username, password);
        if (responseModel.isSuccess() && responseModel.getData() != null) {
            String token = (String) responseModel.getData();
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);

            Cookie cookie = new Cookie(tokenHeader, tokenHead + token);
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute(tokenHeader, tokenHead + token);


            User user2 = userService.getByUserName(username);
            Integer roleid = roleService.getRoleByUserId(user2.getId());
            session.setAttribute("userRoleId", roleid);
            Cookie cookie2 = new Cookie("userRoleId", ""+roleid);
            response.addCookie(cookie2);

            return ResponseModel.SUCCESS(tokenMap);
        } else {
            return ResponseModel.ERROR("账号或密码错误");
        }
    }

    @Resource
    private ZkApi zkApi;

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        boolean node = zkApi.createNode("/configuration", "192.168.12");
        String data = null;
        if (node) {
            data = zkApi.getData("/configuration", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    log.info("【Watcher监听事件】={}", event.getState());
                    log.info("【监听路径为】={}", event.getPath());
                    log.info("【监听的类型为】={}", event.getType()); //  三种监听类型： 创建，删除，更新
                }
            });
        }
        return data;
    }

    private UserVo changeToUserVo(User user, int roleid) {
        UserVo userVo = UserVo.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .roleId(roleid).build();
        return userVo;
    }
}
