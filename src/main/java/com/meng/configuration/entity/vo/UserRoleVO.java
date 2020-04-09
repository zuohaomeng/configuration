package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户角色显示
 * @author HAO.ZUO
 * @date 2020/4/8--23:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleVO implements Serializable {
    private static final long serialVersionUID = -6383848066775320925L;

    /**
     * userRole id,user_role的主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 对应role表中的id，相当于role的类型
     */
    private Integer roleId;
    /**
     * 角色名
     */
    private String roleName;
}
