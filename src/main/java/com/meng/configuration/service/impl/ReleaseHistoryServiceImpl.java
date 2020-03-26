package com.meng.configuration.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meng.configuration.entity.ReleaseHistory;
import com.meng.configuration.entity.vo.HistoryDetailVo;
import com.meng.configuration.entity.vo.HistoryVo;
import com.meng.configuration.mapper.ReleaseHistoryMapper;
import com.meng.configuration.service.ReleaseHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.management.ThreadMXBean;
import java.util.*;


/**
 * @author Hao.ZUO
 * @date 2020/3/12--22:55
 */
@Slf4j
@Service
public class ReleaseHistoryServiceImpl extends ServiceImpl<ReleaseHistoryMapper, ReleaseHistory> implements ReleaseHistoryService, IService<ReleaseHistory> {
    @Resource
    private ReleaseHistoryMapper releaseHistoryMapper;

    @Override
    public ReleaseHistory selectNow(Integer itemId, Integer env) {
        ReleaseHistory releaseHistory = releaseHistoryMapper.selectOne(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getItemId, itemId)
                .eq(ReleaseHistory::getEnv, env)
                .orderByDesc(ReleaseHistory::getIssueVersion)
                .last("limit 1"));
        return releaseHistory;
    }

    @Override
    public int insert(ReleaseHistory releaseHistory) {
        return releaseHistoryMapper.insert(releaseHistory);
    }

    @Override
    public List<HistoryVo> getHistoryList(Integer projectId, Integer env) {
        List<ReleaseHistory> releaseHistories = releaseHistoryMapper.selectList(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getProjectId, projectId)
                .eq(ReleaseHistory::getEnv, env)
                .groupBy(ReleaseHistory::getIssueVersion)
                .orderByDesc(ReleaseHistory::getIssueVersion));

        List<HistoryVo> historyVos = new ArrayList<>();
        for (int i = 0; i < releaseHistories.size(); i++) {
            ReleaseHistory temp = releaseHistories.get(i);
            HistoryVo historyVo = HistoryVo.builder()
                    .Name(temp.getUpdateName())
                    .env(env)
                    .projectId(projectId)
                    .date(DateUtil.formatDateTime(temp.getUpdateTime()))
                    .version(temp.getIssueVersion())
                    .build();
            historyVos.add(historyVo);
        }

        return historyVos;
    }

    @Override
    public List<HistoryDetailVo> getHistoryDetail(Integer projectId, Integer env, Integer version) {
        List<ReleaseHistory> releaseHistories = releaseHistoryMapper.selectList(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getProjectId, projectId)
                .eq(ReleaseHistory::getEnv, env)
                .eq(ReleaseHistory::getIssueVersion, version));
        List<HistoryDetailVo> vos = new ArrayList<>();
        for (int i = 0; i < releaseHistories.size(); i++) {
            ReleaseHistory temp = releaseHistories.get(i);
            HistoryDetailVo vo = HistoryDetailVo.builder()
                    .key(temp.getIssueKey())
                    .oldValue(temp.getOldValue())
                    .newValue(temp.getNewValue())
                    .type((temp.getOldValue()==null||temp.getOldValue().equals(""))?"新增":"修改")
                    .build();
            vos.add(vo);
        }
        return vos;
    }
}
