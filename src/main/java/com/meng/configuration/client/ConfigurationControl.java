package com.meng.configuration.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author HAO.ZUO
 * @date 2020/4/11--19:28
 */
@Slf4j
@Component
public class ConfigurationControl implements ApplicationRunner {
    private ConfigurationRegister register = new ConfigurationRegister();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始拉取数据");


        log.info("结束拉取");
    }
}
