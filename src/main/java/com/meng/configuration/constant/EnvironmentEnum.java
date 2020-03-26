package com.meng.configuration.constant;

/**
 * 环境部署常量
 *
 * @author Hao.ZUO
 * @date 2020/3/26--15:17
 */
public enum EnvironmentEnum {
    DEV(1, "dev"),      //开发环境
    TEST(2, "test"),    //测试环境
    PRE(3, "pre"),      //灰度发布环境
    PRO(4, "pro");      //生产环境

    //编号
    private int id;
    //名称
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    EnvironmentEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
