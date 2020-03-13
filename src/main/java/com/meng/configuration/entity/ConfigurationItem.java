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
    private Integer id;

    /**
     * 发布的key
     */
    private String issueKey;

    /**
     * 发布的value
     */
    private String issueValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * version
     */
    private Integer version;

    /**
     * status = 1   ：已发布
     * updateStatus=1 ：value有修改
     * updateStatus=0 ：没有修改或者修改已发布
     * 发布状态
     */
    private Integer status;

    /**
     * 修改后的key
     */
    private String newKey;

    /**
     * 修改后的value
     */
    private String newValue;
    /**
     *
     * 最新修改人
     */
    private String updateName;
    /**
     * 最新更新时间
     */
    private Date updateTime;

    /**
     * 修改状态
     */
    private Integer updateStatus;

    /**
     * 所属项目id
     */
    private Integer projectId;
    /**
     * 有效位
     */
    private Integer validStatus;

    /**
     * 创建时间
     */
    private Date createTime;
}
