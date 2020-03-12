package com.meng.configuration.service;

import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.util.ResponseModel;

import java.util.List;

/**
 * @author 梦醉
 * @date 2020/3/12--1:55
 */
public interface ConfigurationItemService {
    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    List<ConfigurationItem> selectByPage(int page,int limit);

    /**
     * 获取数量
     * @return
     */
    int getCount();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ConfigurationItem selectById(Integer id);

    /**
     * 添加
     * @param configurationItem
     * @return
     */
    ResponseModel add(ConfigurationItem configurationItem);

    /**
     * 更新
     * @param configurationItem
     * @return
     */
    int update(ConfigurationItem configurationItem);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);
}
