package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.mapper.ConfigurationItemMapper;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 梦醉
 * @date 2020/3/12--1:56
 */
@Slf4j
@Service
public class ConfigurationItemServiceImpl implements ConfigurationItemService {

    @Resource
    private ConfigurationItemMapper itemMapper;

    @Override
    public List<ConfigurationItem> selectByPage(int page, int limit) {
        String limitSql = "limit " + (page - 1) * limit + ", " + limit;
        List<ConfigurationItem> items = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .last(limitSql));
        return items;
    }

    @Override
    public int getCount() {
        int count = itemMapper.selectCount(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1));
        return count;
    }

    @Override
    public ConfigurationItem selectById(Integer id) {

        return itemMapper.selectById(id);
    }

    @Override
    public ResponseModel add(ConfigurationItem configurationItem) {
        LambdaQueryWrapper<ConfigurationItem> wrapper = new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus,1)
                .eq(ConfigurationItem::getKey, configurationItem.getKey())
                .last("limit 1");
        ConfigurationItem item1 = itemMapper.selectOne(wrapper);
        if(item1 != null){
            return ResponseModel.ERROR("key已经存在");
        }
        ConfigurationItem item = ConfigurationItem.builder()
                .version(0)
                .status(0)
                .newKey(configurationItem.getNewKey())
                .newValue(configurationItem.getNewValue())
                .updateStatus(1)
                .validStatus(1)
                .createTime(new Date())
                .build();
        int result = itemMapper.insert(item);
        if(result>0){
            return ResponseModel.SUCCESS("成功");
        }
        return ResponseModel.ERROR("添加失败");
    }

    @Override
    public int update(ConfigurationItem configurationItem) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}
