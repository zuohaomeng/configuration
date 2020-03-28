package com.meng.configuration.controller;

import com.meng.configuration.entity.User;
import com.meng.configuration.service.InformationService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 个人信息相关
 *
 * @author HAO.ZUO
 * @date 2020/3/27--22:09
 */
@Slf4j
@Controller
@RequestMapping("/information")
public class InformationController {

    @Resource
    private InformationService informationService;


    //前往更新页面
    @RequestMapping("to-update")
    public String toUpdate() {
        log.info("[Information, to-update]");
        return "information/userUpdate";
    }



    @ResponseBody
    @RequestMapping("/update")
    public ResponseModel update(String password,String passwordAgain) {
        if(!password.trim().equals(passwordAgain.trim())){
            ResponseModel.ERROR("两次密码不同，请重新输入");
        }

        return ResponseModel.SUCCESS();
    }


}
