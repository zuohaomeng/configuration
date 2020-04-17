package com.meng.configuration.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.entity.Project;
import com.meng.configuration.entity.ReleaseHistory;
import com.meng.configuration.entity.vo.ConfigurationItemVo;
import com.meng.configuration.mapper.ConfigurationItemMapper;
import com.meng.configuration.mapper.ProjectMapper;
import com.meng.configuration.mapper.ReleaseHistoryMapper;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.service.ReleaseHistoryService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ReleaseHistoryMapper releaseHistoryMapper;

    @Override
    public List<ConfigurationItem> selectByPage(int page, int limit, int env, int projectId) {
        String limitSql = "limit " + (page - 1) * limit + ", " + limit;
        List<ConfigurationItem> items = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .eq(ConfigurationItem::getEnv, env)
                .last(limitSql));
        return items;
    }

    @Override
    public List<ConfigurationItemVo> selectVoByPage(int page, int limit, int env, int projectId) {
        List<ConfigurationItem> items = selectByPage(page, limit, env, projectId);
        List<ConfigurationItemVo> itemVos = new ArrayList<>();
        for (ConfigurationItem item : items) {
            ReleaseHistory releaseHistory = releaseHistoryService.selectNow(item.getId(), env);
            ConfigurationItemVo vo = ConfigurationItemVo.builder()
                    .id(item.getId())
                    .remark(item.getRemark())
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
    public int getCountByProjectId(Integer projectId, int env) {
        int count = itemMapper.selectCount(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .eq(ConfigurationItem::getEnv, env));
        return count;
    }

    @Override
    public ConfigurationItem selectById(Integer id) {
        return itemMapper.selectById(id);
    }

    @Override
    public ConfigurationItemVo selectVoById(Integer id) {
        ConfigurationItem item = selectById(id);
        ConfigurationItemVo vo = ConfigurationItemVo.builder()
                .id(item.getId())
                .key(item.getNewKey())
                .value(item.getNewValue())
                .remark(item.getRemark())
                .build();
        return vo;
    }


    @Override
    public ResponseModel add(ConfigurationItem configurationItem) {
        LambdaQueryWrapper<ConfigurationItem> wrapper = new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getNewKey, configurationItem.getNewKey())
                .eq(ConfigurationItem::getProjectId, configurationItem.getProjectId())
                .eq(ConfigurationItem::getEnv, configurationItem.getEnv())
                .last("limit 1");
        ConfigurationItem item1 = itemMapper.selectOne(wrapper);
        if (item1 != null) {
            return ResponseModel.ERROR("key已经存在");
        }
        ConfigurationItem item = ConfigurationItem.builder()
                .issueKey("")
                .issueValue("")
                .remark(configurationItem.getRemark())
                .version(0)
                .status(0)
                .newKey(configurationItem.getNewKey())
                .newValue(configurationItem.getNewValue())
                .updateName("李四")
                .updateTime(new Date())
                .updateStatus(1)
                .projectId(configurationItem.getProjectId())
                .env(configurationItem.getEnv())
                .validStatus(1)
                .createTime(new Date())
                .build();
        int result = itemMapper.insert(item);
        if (result > 0) {
            return ResponseModel.SUCCESS("添加成功");
        }
        return ResponseModel.ERROR("添加失败");
    }

    /**
     * 添加修改历史
     *
     * @param item
     */
    private void insertHistory(ConfigurationItem item) {
        ReleaseHistory releaseHistory = ReleaseHistory.builder()
                .itemId(item.getId())
                .issueKey(item.getNewKey())
                .oldValue("")
                .newValue(item.getNewValue())
                .issueVersion(projectService.selectById(item.getProjectId()).getVersion() + 1)
                .updateName("张三")
                .updateTime(new Date())
                .build();
        releaseHistoryService.insert(releaseHistory);
    }

    @Override
    public int update(ConfigurationItem newItem) {
        ConfigurationItem oldItem = selectById(newItem.getId());
        newItem.setUpdateName("lisi");
        newItem.setUpdateTime(new Date());
        if (!newItem.getNewValue().equals(oldItem.getIssueValue())) {
            newItem.setUpdateStatus(1);
        } else {
            newItem.setUpdateStatus(0);
        }
        itemMapper.update(newItem);
        return 0;
    }

    @Override
    public int delete(Integer id) {
        int result = itemMapper.delete(id);
        return result;
    }


    @Override
    public int release(Integer projectId) {
        Project project = projectService.selectById(projectId);
        List<ConfigurationItem> itemList = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .eq(ConfigurationItem::getUpdateStatus, 1));
        if (itemList.size() <= 0) {
            return -2;
        }
        //版本号添加1
        itemMapper.release(project.getVersion() + 1, projectId);
        projectMapper.incrementVersion(projectId);

        //记录发布历史
        List historyList = new ArrayList();
        for (int i = 0; i < itemList.size(); i++) {
            ConfigurationItem item = itemList.get(i);
            ReleaseHistory history = ReleaseHistory.builder()
                    .issueKey(item.getNewKey())
                    .issueVersion(project.getVersion() + 1)
                    .newValue(item.getNewValue())
                    .oldValue(item.getIssueValue())
                    .itemId(item.getId())
                    .env(item.getEnv())
                    .updateName("lisi")
                    .updateTime(new Date())
                    .build();
            historyList.add(history);
        }
        releaseHistoryService.saveBatch(historyList);
        return 1;
    }

    @Override
    public int rollBalck(Integer projectId) {
        Project project = projectService.selectById(projectId);
        int version = project.getVersion();
        //删除history
        List<ReleaseHistory> releaseHistories = releaseHistoryMapper.selectList(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getIssueVersion, version));
        List historyDeleteList = new ArrayList();
        if (releaseHistories.size() > 0) {
            for (int i = 0; i < releaseHistories.size(); i++) {
                ReleaseHistory releaseHistory = releaseHistories.get(i);
                historyDeleteList.add(releaseHistory.getId());
                itemMapper.rollBalck(releaseHistory.getOldValue(), releaseHistory.getItemId());
            }
            releaseHistoryMapper.deleteBatchIds(historyDeleteList);
        }
        return 1;
    }

    @Override
    public Map getAllItem(Integer projectId, Integer env) {
        List<ConfigurationItem> items = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                .select(ConfigurationItem::getIssueKey, ConfigurationItem::getIssueValue)
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .eq(ConfigurationItem::getEnv, env)
                .eq(ConfigurationItem::getStatus, 1));
        Map map = new HashMap();
        for (int i = 0; i < items.size(); i++) {
            ConfigurationItem item = items.get(i);
            map.put(item.getIssueKey(),item.getIssueValue());
        }
        return map;
    }
}
