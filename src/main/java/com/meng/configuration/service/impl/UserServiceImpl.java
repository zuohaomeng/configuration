package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.User;
import com.meng.configuration.entity.vo.UserAddVO;
import com.meng.configuration.mapper.UserMapper;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.AESUtil;
import com.meng.configuration.util.JwtTokenUtil;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserDetailsService userDetailsService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleServiceImpl roleService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public ResponseModel loginIn(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            log.error("[loginIn] error,userName or password is null");
            return ResponseModel.ERROR("账号或者密码为空！");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            log.info("[loginIn] info, userName or password error");
            return ResponseModel.ERROR("账号或者密码错误");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseModel.SUCCESS("登录成功", token);
    }

    @Override
    public ResponseModel register(UserAddVO userAddVO) {
        if (StringUtils.isEmpty(userAddVO.getUsername()) || StringUtils.isEmpty(userAddVO.getPassword())) {
            log.error("[register] error, userName or passord is null");
            return ResponseModel.ERROR("账号或者密码为空！");
        }
        if (!userAddVO.getPassword().trim().equals(userAddVO.getPasswordAgain().trim())) {
            return ResponseModel.ERROR("两次密码不同，请重新数据！");
        }
        User userSelect = userMapper.hasUserByUserName(userAddVO.getUsername());
        if (userSelect == null) {
            String pwd = passwordEncoder.encode(userAddVO.getPassword());
            User user = User.builder()
                    .username(userAddVO.getUsername())
                    .password(pwd)
                    .name(userAddVO.getName())
                    .validStatus(1)
                    .createTime(new Date())
                    .build();

            int result = userMapper.insert(user);
            roleService.addRoleRelation(userAddVO.getRoleId(), user.getId());
            if (result > 0) {
                return ResponseModel.SUCCESS("添加成功！");
            }
        }
        log.error("[register] error, insert fail,userName={}", userAddVO.getUsername());
        return ResponseModel.ERROR("当前账号已存在！");
    }

    @Override
    public User getByUserName(String userName) {
        log.info("[UserServiceImpl getByUserName],userName={}", userName);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userName)
                .eq(User::getValidStatus, 1)
                .last("limit 1"));
        return user;
    }

    @Override
    public Integer updatePwdByUserId(Integer userId, String pwd) {
        String encodePwd = passwordEncoder.encode(pwd);
        return userMapper.updatePwdByUserId(userId,encodePwd);
    }

    @Override
    public Integer getCount() {
        return userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getValidStatus, 1));
    }
}
