package com.meng.configuration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO  项目
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 14:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {

    private static final long serialVersionUID = 8691880417123010703L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * server_id  应用唯一标识 应用程序链接配置中心使用
     */
    private  String projectId;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人姓名
     */
    private String leaderName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 有效状态 1为有效，0为删除
     */
    private Integer validStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
