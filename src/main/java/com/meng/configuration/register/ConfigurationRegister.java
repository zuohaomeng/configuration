package com.meng.configuration.register;


import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author HAO.ZUO
 * @date 2020/4/10--18:47
 */
@Slf4j
@Component
public class ConfigurationRegister implements ApplicationRunner {

    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private ZooKeeper zkClient;

    public String path = "/configuration/node";

    public String data = "127.0.0.1:8989";

    public String nodePath = null;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (nodePath == null) {
                        nodePath = zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                        log.info("[the result of create,nodePath={}]", nodePath);
                    }
                } catch (Exception e) {
                    log.error("[error,{}]", e);
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}
