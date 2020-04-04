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
     * 注册
     */
    ResponseModel register(UserAddVO userAddVO);

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    ResponseModel loginIn(String userName,String password);

    User getByUserName(String userName);
}
