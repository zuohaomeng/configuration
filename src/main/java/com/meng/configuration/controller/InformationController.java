package com.meng.configuration.controller;

import com.meng.configuration.entity.vo.UserAddVO;
import com.meng.configuration.entity.vo.UserVo;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    private UserService userService;

    //前往更新页面
    @RequestMapping("to-update")
    public String toUpdate() {
        log.info("[Information, to-update]");
        return "information/userUpdate";
    }



    @ResponseBody
    @RequestMapping("/update")
    public ResponseModel update(@RequestBody UserAddVO userAddVO, HttpServletRequest request) {
        String password = userAddVO.getPassword();
        String passwordAgain = userAddVO.getPasswordAgain();
        if(!password.trim().equals(passwordAgain.trim())){
            ResponseModel.ERROR("两次密码不同，请重新输入");
        }
        HttpSession session = request.getSession();
        UserVo user = (UserVo)session.getAttribute("user");
        userService.updatePwdByUserId(user.getId(), password);
        return ResponseModel.SUCCESS();
    }


}
