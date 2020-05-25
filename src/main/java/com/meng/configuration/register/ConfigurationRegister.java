package com.meng.configuration.register;


import com.meng.configuration.entity.AddressNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author HAO.ZUO
 * @date 2020/4/10--18:47
 */
@Slf4j
@Component
public class ConfigurationRegister implements ApplicationRunner {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    @Autowired
    private ZooKeeper zkClient;
    public String path = "/configuration/address/node";
    public String parentPath = "/configuration/address";
    public String data = "127.0.0.1:8989";
    public String nodePath = null;


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (nodePath == null) {
//                        //CreateMode.EPHEMERAL_SEQUENTIAL   临时顺序
//                        //CreateMode.PERSISTENT_SEQUENTIAL  持久顺序
//                        nodePath = zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//                        log.info("[the result of create,nodePath={}]", nodePath);
//                    }
//                } catch (Exception e) {
//                    log.error("[error,{}]", e);
//                }
//                countDownLatch.countDown();
//            }
//        });
//        countDownLatch.await();

        try {
            ArrayList<AddressNode> list = new ArrayList<>();
            List<String> children = zkClient.getChildren(parentPath, false);
            for (int i = 0; i < children.size(); i++) {
                byte[] data = zkClient.getData(parentPath + "/" + children.get(i), true, new Stat());
                String s = new String(data);
                String[] split = s.split(":");

                AddressNode address = new AddressNode(split[0], split[1]);
                list.add(address);
            }
                AddressNodeService.change(list);
        } catch (Exception e) {
            log.error("[error,{}]", e);
        }


        //每5分钟拉去一次配置中心的服务器地址
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<AddressNode> list = new ArrayList<>();
                    List<String> children = zkClient.getChildren(parentPath, false);
                    for (int i = 0; i < children.size(); i++) {
                        byte[] data = zkClient.getData(parentPath + "/" + children.get(i), true, new Stat());
                        String s = new String(data);
                        String[] split = s.split(":");

                        AddressNode address = new AddressNode(split[0], split[1]);
                        list.add(address);
                    }
                    AddressNodeService.change(list);
                } catch (Exception e) {
                    log.error("[error,{}]", e);
                }

            }
        }, 5, 5, TimeUnit.MINUTES);


    }
}
