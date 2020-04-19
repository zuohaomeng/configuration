package com.meng.configuration.client;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存读取到的配置
 */
@Slf4j
public class ConfigMap {
    //保存配置
    private Map<String, String> map = new HashMap<>();

    private List<ConfigItemHandler> list = new ArrayList<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<ConfigItemHandler> getList() {
        return list;
    }

    public void setList(List<ConfigItemHandler> list) {
        this.list = list;
    }

    public void addConfigItemConfig(ConfigItemHandler handler) {
        list.add(handler);
    }

    public boolean removeConfigItemConfig(ConfigItemHandler handler) {
        return list.remove(handler);
    }

    public void updateItem(){
        ConfigItemHandler myConfig = new MyConfig();
        Class<? extends ConfigItemHandler> aClass = myConfig.getClass();
        System.out.println(aClass.getName());
        Field[] fields =aClass.getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            boolean annotationPresent = field.isAnnotationPresent(Item.class);
            if(annotationPresent){
                Item annotation = field.getAnnotation(Item.class);
                try {
                    field.set(field.getName(),map.get(annotation.key()));
                } catch (IllegalAccessException e) {
                    log.error("[updateItem error]",e);
                }
            }

        }
    }

    public static void main(String[] args) throws IllegalAccessException {

    }

}
