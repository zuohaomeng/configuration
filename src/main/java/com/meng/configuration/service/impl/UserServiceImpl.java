package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.User;
import com.meng.configuration.mapper.UserMapper;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.AESUtil;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 15:18
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseModel register(String userNumber, String password) {
        if (StringUtils.isEmpty(userNumber) || StringUtils.isEmpty(password)) {
            log.error("[register] error, userNumber or passord is null");
            return ResponseModel.ERROR("账号或者密码为空！");
        }
        User userSelect = userMapper.selectByUserNumberNotPwd(userNumber);
        if (userSelect == null ) {
            String pwd = AESUtil.encode(password);
            User user = User.builder()
                    .userNumber(userNumber)
                    .password(pwd)
                    .name(userNumber)
                    .validStatus(1)
                    .createTime(new Date())
                    .build();
            int result = userMapper.insert(user);
            if (result > 0) {
                return ResponseModel.SUCCESS();
            }
        }
        log.error("[register] error, insert fail,userNumber={}", userNumber);
        return ResponseModel.ERROR("插入失败");
    }

    @Override
    public ResponseModel loginIn(String userNumber, String password) {
        if (StringUtils.isEmpty(userNumber) || StringUtils.isEmpty(password)) {
            log.error("[loginIn] error,userNumber or password is null");
            return ResponseModel.ERROR("账号或者密码为空！");
        }
        String pwd = AESUtil.encode(password);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getValidStatus, 1)
                .eq(User::getUserNumber, userNumber)
                .eq(User::getPassword, pwd));
        if(user== null){
            log.info("[loginIn] info, userNumber or password error");
            return ResponseModel.ERROR("账号或者密码错误");
        }
        user.setPassword(null);
        return ResponseModel.SUCCESS(user);
    }
}
