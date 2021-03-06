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

    /**
     * 更新配置项
     */
    public void updateItem() {
        for (int i = 0; i < list.size(); i++) {
            //获取一个配置类
            ConfigItemHandler config = list.get(i);

            Class<? extends ConfigItemHandler> aClass = config.getClass();
            log.info("[the class of ConfigItemHandler],class={}", aClass.getName());

            //遍历所有属性
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                //打开私用访问
                field.setAccessible(true);
                //如果方法上有Item注解
                boolean annotationPresent = field.isAnnotationPresent(Item.class);
                if (annotationPresent) {
                    Item annotation = field.getAnnotation(Item.class);
                    try {
                        //valueA为保存的配置
                        String valueA = map.get(annotation.key());
                        //原始的配置
                        Object valueB = field.get(config);
                        if (valueA == null) {
                            field.set(config, null);
                            log.info("[updateItem],field={},old={},new={}", field.getName(), valueB, valueA);
                            //如果不同就更新
                        } else if (valueB == null || !valueA.equals(valueB.toString())) {
                            field.set(config, valueA);
                            log.info("[updateItem],field={},old={},new={}", field.getName(), valueB, valueA);
                        }

                    } catch (IllegalAccessException e) {
                        log.error("[updateItem error]", e);
                    }
                }

            }
        }

    }


}
