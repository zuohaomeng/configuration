package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目视图
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/20 16:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectVo implements Serializable {
    private static final long serialVersionUID = -6995514665292547643L;

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
     * 负责人姓名
     */
    private String leaderName;

    /**
     * 更新时间
     */
    private String updateTime;
}
