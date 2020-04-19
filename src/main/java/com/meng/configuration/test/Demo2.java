package com.meng.configuration.test;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import java.lang.reflect.Field;

public class Demo2 {
    public static void main(String[] args) {
        DemoTest demoTest = new DemoTest();
        Class<? extends DemoTest> aClass = demoTest.getClass();
        Field[] fields = aClass.getDeclaredFields();
        System.out.println(demoTest.toString());
        for (Field f:fields) {
            f.setAccessible(true);
            try {
                f.set(demoTest, "321");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(demoTest.toString());
    }
}
