package com.meng.configuration.test;

import cn.hutool.core.date.DateUtil;
import com.meng.configuration.client.ConfigMap;
import com.meng.configuration.client.ConfigManager;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ConfigTest {
    public static void main(String[] args) throws IllegalAccessException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DemoTest demoTest = new DemoTest();

        ConfigMap configMap = new ConfigMap();
        configMap.addConfigItemConfig(demoTest);

        String env = "test";
        String project = "dubbo-shop";
        ConfigManager control = new ConfigManager(configMap, env, project);
        control.start();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = configMap.getMap();
                System.out.println("当前版本为："+control.getVersion());
                for (Map.Entry<String, String> entry : map.entrySet()) {

                    System.out.println("当前时间：" + DateUtil.now() + "\t" + entry.getKey() + "=" + entry.getValue());
                }

            }
        }, 30, 30, TimeUnit.SECONDS);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
