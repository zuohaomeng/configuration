package com.meng.configuration.component;

/**
 * 环境常量
 */
public enum EnvConstant {
    DEV(1,"dev"),
    TEST(2,"test"),
    PRE(3,"pre"),
    PRO(4,"pro");

    EnvConstant(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    private Integer id;
    private String value;

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
