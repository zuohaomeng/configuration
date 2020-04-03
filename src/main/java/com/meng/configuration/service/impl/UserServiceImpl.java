package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.User;
import com.meng.configuration.entity.vo.UserAddVO;
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
    public ResponseModel register(UserAddVO userAddVO) {
        if (StringUtils.isEmpty(userAddVO.getUserNumber()) || StringUtils.isEmpty(userAddVO.getPassword())) {
            log.error("[register] error, userNumber or passord is null");
            return ResponseModel.ERROR("账号或者密码为空！");
        }
        if(!userAddVO.getPassword().trim().equals(userAddVO.getPasswordAgain().trim())){
            return ResponseModel.ERROR("两次密码不同，请重新数据！");
        }
        User userSelect = userMapper.hasUserByUserNumber(userAddVO.getUserNumber());
        if (userSelect == null ) {
            String pwd = AESUtil.encode(userAddVO.getPassword());
            User user = User.builder()
                    .userNumber(userAddVO.getUserNumber())
                    .password(pwd)
                    .name(userAddVO.getName())
                    .validStatus(1)
                    .createTime(new Date())
                    .build();
            int result = userMapper.insert(user);
            if (result > 0) {
                return ResponseModel.SUCCESS("添加成功！");
            }
        }
        log.error("[register] error, insert fail,userNumber={}", userAddVO.getUserNumber());
        return ResponseModel.ERROR("用户添加失败！");
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

    @Override
    public User getByUserNumber(String userNumber) {
        log.info("[UserServiceImpl getByUserNumber],userNumber={}",userNumber);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserNumber, userNumber)
                .eq(User::getValidStatus, 1)
                .last("limit 1"));
        return user;
    }
}
