package com.meng.configuration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO 配置项
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 17:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ConfigurationItem")
public class ConfigurationItem implements Serializable {
    private static final long serialVersionUID = 4997754470334747495L;

    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private String value;

    /**
     * version
     */
    private Integer version;

    /**
     * 发布状态
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date issue_time;
    /**
     * 修改后的key
     */
    private String newKey;

    /**
     * 修改后的value
     */
    private String newValue;

    /**
     * 修改状态
     */
    private Integer updateStatus;

    /**
     * 有效位
     */
    private Integer validStatus;

    /**
     * 创建时间
     */
    private Date createTime;
}
