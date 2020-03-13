package com.meng.configuration.service;

import com.meng.configuration.entity.ReleaseHistory;

/**
 * @author Hao.ZUO
 * @date 2020/3/12--22:54
 */
public interface ReleaseHistoryService {
    /**
     * 根据ItemId查询出最新的发布记录
     * @param itemId
     * @return
     */
    ReleaseHistory selectNow(Integer itemId);

    /**
     * 新增
     * @param releaseHistory
     * @return
     */
    int insert(ReleaseHistory releaseHistory);
}
