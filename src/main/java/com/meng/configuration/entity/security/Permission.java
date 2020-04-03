package com.meng.configuration.entity.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HAO.ZUO
 * @date 2020/3/31--13:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission implements Serializable {
    private static final long serialVersionUID = -3214283749305260650L;
    private Long id;
    /**
     * 父权限
     */
    private Long parentId;
    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限英文名称
     */
    private String enname;

    /**
     * 备注
     */
    private String description;

    private Date created;

    private Date updated;
}
