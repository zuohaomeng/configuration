package com.meng.configuration.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.entity.ReleaseHistory;
import com.meng.configuration.entity.vo.ConfigurationItemVo;
import com.meng.configuration.mapper.ConfigurationItemMapper;
import com.meng.configuration.mapper.ReleaseHistoryMapper;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.service.ReleaseHistoryService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private ReleaseHistoryService releaseHistoryService;

    @Resource
    private ProjectService projectService;

    @Override
    public List<ConfigurationItem> selectByPage(int page, int limit,int projectId) {
        String limitSql = "limit " + (page - 1) * limit + ", " + limit;
        List<ConfigurationItem> items = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .last(limitSql));
        return items;
    }

    @Override
    public List<ConfigurationItemVo> selectVoByPage(int page, int limit,int projectId) {
        List<ConfigurationItem> items = selectByPage(page, limit,projectId);
        List<ConfigurationItemVo> itemVos = new ArrayList<>();
        for (ConfigurationItem item : items) {
            ReleaseHistory releaseHistory = releaseHistoryService.selectNow(item.getId());
            ConfigurationItemVo vo = ConfigurationItemVo.builder()
                    .id(item.getId())
                    .updateName(releaseHistory.getUpdateName())
                    .updateTime(DateUtil.formatDateTime(releaseHistory.getUpdateTime()))
                    .build();
            if (item.getStatus() == 1 && item.getUpdateStatus() == 0) {
                vo.setKey(item.getIssueKey());
                vo.setValue(item.getIssueValue());
            } else {
                vo.setKey(item.getNewKey());
                vo.setValue(item.getNewValue());
                vo.setStatus("有修改");
            }
            itemVos.add(vo);
        }
        return itemVos;
    }

    @Override
    public int getCountByProjectId(Integer projectId){
        int count = itemMapper.selectCount(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId,projectId));
        return count;
    }

    @Override
    public ConfigurationItem selectById(Integer id) {
        return itemMapper.selectById(id);
    }

    @Override
    public ResponseModel add(ConfigurationItem configurationItem) {
        LambdaQueryWrapper<ConfigurationItem> wrapper = new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getNewKey, configurationItem.getNewKey())
                .last("limit 1");
        ConfigurationItem item1 = itemMapper.selectOne(wrapper);
        if (item1 != null) {
            return ResponseModel.ERROR("key已经存在");
        }
        ConfigurationItem item = ConfigurationItem.builder()
                .issueKey("")
                .issueValue("")
                .version(0)
                .status(0)
                .issueTime(new Date())
                .newKey(configurationItem.getNewKey())
                .newValue(configurationItem.getNewValue())
                .updateStatus(1)
                .projectId(configurationItem.getProjectId())
                .validStatus(1)
                .createTime(new Date())
                .build();
        int result = itemMapper.insert(item);
        insertHistory(item);
        if (result > 0) {
            return ResponseModel.SUCCESS("成功");
        }
        return ResponseModel.ERROR("添加失败");
    }

    /**
     * 添加修改历史
     * @param item
     */
    private void insertHistory(ConfigurationItem item) {
        ReleaseHistory releaseHistory = ReleaseHistory.builder()
                .itemId(item.getId())
                .issueKey("")
                .oldValue("")
                .newValue(item.getNewValue())
                .issueVersion(projectService.selectById(item.getProjectId()).getVersion())
                .updateName("张三")
                .updateTime(new Date())
                .build();
        releaseHistoryService.insert(releaseHistory);
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
