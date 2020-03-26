package com.meng.configuration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meng.configuration.entity.ReleaseHistory;
import com.meng.configuration.entity.vo.HistoryDetailVo;
import com.meng.configuration.entity.vo.HistoryVo;

import java.util.List;

/**
 * @author Hao.ZUO
 * @date 2020/3/12--22:54
 */
public interface ReleaseHistoryService extends IService<ReleaseHistory> {
    /**
     * 根据ItemId查询出最新的发布记录
     * @param itemId
     * @return
     */
    ReleaseHistory selectNow(Integer itemId,Integer env);

    /**
     * 新增
     * @param releaseHistory
     * @return
     */
    int insert(ReleaseHistory releaseHistory);

    /**
     * 获取发布列表
     */
    List<HistoryVo> getHistoryList(Integer projectId, Integer env);

    /**
     * 获取单次发布详情
     */
    List<HistoryDetailVo> getHistoryDetail(Integer projectId,Integer env,Integer version);
}
