package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发布历史列表
 *
 * @author HAO.ZUO
 * @date 2020/3/26--22:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryVo {
    /**
     * 发布人
     */
    private String Name;
    /**
     * 发布时间
     */
    private String date;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 环境
     */
    private Integer env;
}
