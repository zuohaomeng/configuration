package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 添加用户，修改密码使用
 * @author HAO.ZUO
 * @date 2020/3/27--23:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddVO implements Serializable {
    private static final long serialVersionUID = -2205207062592895865L;

    private Integer id;

    private String username;

    private String name;

    private String password;

    private String passwordAgain;

    /**
     * 角色
     * 37   管理员
     * 38   高级用户
     * 39   普通用户
     */
    private Integer roleId;
}
