package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meng.configuration.entity.ReleaseHistory;
import com.meng.configuration.mapper.ReleaseHistoryMapper;
import com.meng.configuration.service.ReleaseHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author Hao.ZUO
 * @date 2020/3/12--22:55
 */
@Slf4j
@Service
public class ReleaseHistoryServiceImpl extends ServiceImpl<ReleaseHistoryMapper,ReleaseHistory> implements ReleaseHistoryService, IService<ReleaseHistory> {
    @Resource
    private ReleaseHistoryMapper releaseHistoryMapper;

    @Override
    public ReleaseHistory selectNow(Integer itemId) {
        ReleaseHistory releaseHistory = releaseHistoryMapper.selectOne(new LambdaQueryWrapper<ReleaseHistory>()
                .eq(ReleaseHistory::getItemId, itemId)
                .orderByDesc(ReleaseHistory::getIssueVersion)
                .last("limit 1"));
        return releaseHistory;
    }

    @Override
    public int insert(ReleaseHistory releaseHistory) {
        return releaseHistoryMapper.insert(releaseHistory);
    }
}
