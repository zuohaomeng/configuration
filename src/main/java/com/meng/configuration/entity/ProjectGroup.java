package com.meng.configuration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 梦醉  项目组相关
 * @date 2020/1/21--14:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ProjectGroup")
public class ProjectGroup implements Serializable {

    private static final long serialVersionUID = 6518219506880379078L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 项目组名
     */
    private String groupName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人
     */
    private String leaderName;
    /**
     * 负责人邮箱
     */
    private String email;
    /**
     * 是否有效
     */
    private String validStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
