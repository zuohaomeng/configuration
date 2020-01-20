package com.meng.configuration.service;

import com.meng.configuration.entity.Menu;

import java.util.List;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:09
 */
public interface MenuService {
    /**
     * 获取所有权限
     * @return
     */
    List<Menu> selectAllMenu();
}
