package com.meng.configuration.service;

import com.meng.configuration.entity.User;
import com.meng.configuration.entity.vo.UserAddVO;
import com.meng.configuration.util.ResponseModel;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 15:18
 */
public interface UserService {
    /**
     * 注册,添加用户
     */
    ResponseModel register(UserAddVO userAddVO);

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    ResponseModel loginIn(String userName,String password);

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 更新密码
     * @param userId
     * @param pwd
     * @return
     */
    Integer updatePwdByUserId(Integer userId,String pwd);

    /**
     * 获取用户数量
     * @return
     */
    Integer getCount();
}
