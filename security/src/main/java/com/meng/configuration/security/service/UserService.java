package com.meng.configuration.security.service;

import com.meng.configuration.entity.User;

/**
 * @author HAO.ZUO
 * @date 2020/4/3--21:25
 */
public interface UserService {
    User getByUserNumber(String userNumber);
}
