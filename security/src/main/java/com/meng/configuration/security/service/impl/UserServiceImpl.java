package com.meng.configuration.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.User;
import com.meng.configuration.security.mapper.UserMapper;
import com.meng.configuration.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author HAO.ZUO
 * @date 2020/4/3--21:25
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

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
