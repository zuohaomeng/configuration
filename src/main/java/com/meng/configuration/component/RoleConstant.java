package com.meng.configuration.component;

/**
 * @author HAO.ZUO
 * @date 2020/4/8--23:49
 */
public enum RoleConstant {
    ADMIN(37, "管理员"),
    POWERUSER(38, "高级用户"),
    USER(39, "普通用户");

    private Integer roleId;
    private String roleName;

    RoleConstant(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
