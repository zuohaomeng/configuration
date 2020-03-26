package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发布历史每个配置项的修改
 *
 * @author HAO.ZUO
 * @date 2020/3/26--22:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDetailVo {
    /**
     * 类型
     */
    private String type;
    /**
     * key
     */
    private String key;
    /**
     * 旧的value
     */
    private String oldValue;
    /**
     * 新的value
     */
    private String newValue;
}