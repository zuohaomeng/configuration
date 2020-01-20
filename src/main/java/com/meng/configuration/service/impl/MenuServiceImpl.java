package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.Menu;
import com.meng.configuration.mapper.MenuMapper;
import com.meng.configuration.service.MenuService;
import com.meng.configuration.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO 菜单管理
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:09
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    @Override
    public List<Menu> selectAllMenu() {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<Menu>()
                .in(Menu::getMenuType, 'M', 'C')
                .eq(Menu::getVisible, 0)
                .orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = menuMapper.selectList(menuLambdaQueryWrapper);


        return TreeUtils.getChildPerms(menus, 0);
    }
}
