package com.meng.configuration.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.AddressNode;
import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.entity.Project;
import com.meng.configuration.entity.ReleaseHistory;
import com.meng.configuration.entity.vo.ConfigurationItemVo;
import com.meng.configuration.mapper.ConfigurationItemMapper;
import com.meng.configuration.mapper.ProjectMapper;
import com.meng.configuration.mapper.ReleaseHistoryMapper;
import com.meng.configuration.register.AddressNodeService;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.service.ReleaseHistoryService;
import com.meng.configuration.util.HttpResult;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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
            ConfigurationItemVo vo = null;
            if (releaseHistory != null) {
                vo = ConfigurationItemVo.builder()
                        .id(item.getId())
                        .remark(item.getRemark())
                        .updateName(releaseHistory.getUpdateName())
                        .updateTime(DateUtil.formatDateTime(releaseHistory.getUpdateTime()))
                        .build();
            } else {
                vo = ConfigurationItemVo.builder()
                        .id(item.getId())
                        .remark(item.getRemark())
                        .build();
            }
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
                .updateName("左浩")
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
                .updateName("左浩")
                .updateTime(new Date())
                .build();
        releaseHistoryService.insert(releaseHistory);
    }

    @Override
    public int update(ConfigurationItem newItem) {
        ConfigurationItem oldItem = selectById(newItem.getId());
        newItem.setUpdateName("左浩");
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
        ConfigurationItem item = itemMapper.selectById(id);
        if (item != null) {
            //通知改变
            List list = AddressNodeService.getList();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    sendItemChange(item.getProjectId(), item.getEnv(), (AddressNode) list.get(i));
                }
            }
        }
        int result = itemMapper.delete(id);

        return result;
    }


    @Override
    public int release(Integer projectId, Integer env) {
        Project project = projectService.selectById(projectId);
        List<ConfigurationItem> itemList = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .eq(ConfigurationItem::getUpdateStatus, 1)
                .eq(ConfigurationItem::getEnv, env));
        if (itemList.size() <= 0) {
            return -2;
        }

        itemMapper.release(project.getVersion() + 1, projectId, env);
        //版本号添加1
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
                    .updateName("左浩")
                    .updateTime(new Date())
                    .projectId(item.getProjectId())
                    .build();
            historyList.add(history);
        }
        releaseHistoryService.saveBatch(historyList);

        //通知改变
        List list = AddressNodeService.getList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                sendItemChange(projectId, env, (AddressNode) list.get(i));
            }
        }

        return 1;
    }

    @Override
    public int rollBalck(Integer projectId, Integer env) {
        //获取最新的发布版本号
        ReleaseHistory history = releaseHistoryMapper.selectOne(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getProjectId, projectId)
                .eq(ReleaseHistory::getEnv, env)
                .orderByDesc(ReleaseHistory::getIssueVersion)
                .last("limit 1"));
        if (history == null) {
            return 0;
        }

        //删除history
        List<ReleaseHistory> releaseHistories = releaseHistoryMapper.selectList(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getIssueVersion, history.getIssueVersion())
                .eq(ReleaseHistory::getEnv, env));
        List historyDeleteList = new ArrayList();
        if (releaseHistories.size() > 0) {
            for (int i = 0; i < releaseHistories.size(); i++) {
                ReleaseHistory releaseHistory = releaseHistories.get(i);
                historyDeleteList.add(releaseHistory.getId());
                if (releaseHistory.getOldValue() == null || releaseHistory.getOldValue().equals("")) {
                    itemMapper.delete(releaseHistory.getItemId());
                } else {
                    itemMapper.rollBalck(releaseHistory.getOldValue(), releaseHistory.getItemId());
                }
            }
            releaseHistoryMapper.deleteBatchIds(historyDeleteList);
        }
        //通知改变
        List list = AddressNodeService.getList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                sendItemChange(projectId, env, (AddressNode) list.get(i));
            }
        }

        return 1;
    }

    @Override
    public Map getAllItem(Integer projectId, Integer env) {
        List<ConfigurationItem> items = itemMapper.selectList(new LambdaQueryWrapper<ConfigurationItem>()
                //表示已经发布了的
                .select(ConfigurationItem::getIssueKey, ConfigurationItem::getIssueValue)
                .eq(ConfigurationItem::getValidStatus, 1)
                .eq(ConfigurationItem::getProjectId, projectId)
                .eq(ConfigurationItem::getEnv, env)
                .eq(ConfigurationItem::getStatus, 1));
        Map map = new HashMap();
        for (int i = 0; i < items.size(); i++) {
            ConfigurationItem item = items.get(i);
            map.put(item.getIssueKey(), item.getIssueValue());
        }
        return map;
    }


    @Async
    public void sendItemChange(Integer projectId, Integer envId, AddressNode address) {
        log.info("[ConfigurationItemServiceImpl-sendItemChange],发送配置改变信息");
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        int version = 0;
        if (PullServiceImpl.versionMap.containsKey(projectId + "+" + envId)) {
            version = (Integer) PullServiceImpl.versionMap.get(projectId + "+" + envId) + 1;
        }
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://" + address.getAddress() + ":" + address.getPort()
                + "/http/itemchange?projectId=" + projectId + "&envId=" + envId + "&version=" + version);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            System.out.println("响应状态为:" + response.getStatusLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
