package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 常用保存数据
 * @author HAO.ZUO
 * @date 2020/4/8--21:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVo implements Serializable {
    private static final long serialVersionUID = -4069782269887504085L;

    private Integer id;

    private String username;

    private String name;
    /**
     * 角色
     * 37   管理员
     * 38   高级用户
     * 39   普通用户
     */
    private Integer roleId;
}
