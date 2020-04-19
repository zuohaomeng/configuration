package com.meng.configuration.test;

import com.meng.configuration.client.ConfigMap;
import com.meng.configuration.client.ConfigurationControl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ConfigTest {
    public static void main(String[] args) throws IllegalAccessException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DemoTest demoTest = new DemoTest();
        ConfigMap configMap = new ConfigMap();
        configMap.addConfigItemConfig(demoTest);
        String env = "dev";
        String project = "dubbo_add";
        ConfigurationControl control = new ConfigurationControl(configMap, env, project);

        control.start();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(demoTest.toString());
            }
        },30 , 30, TimeUnit.SECONDS);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
