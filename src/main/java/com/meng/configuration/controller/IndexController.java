package com.meng.configuration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 11:44
 */
@Slf4j
@Controller
@RequestMapping
public class IndexController {


    @GetMapping("/index")
    public String index(){
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {

        log.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());

        log.info(oAuth2Authentication.toString());

        log.info("principal.toString() " + principal.toString());

        log.info("principal.getName() " + principal.getName());

        log.info("authentication: " + authentication.getAuthorities().toString());

        return oAuth2Authentication;
    }
}
