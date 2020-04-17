package com.meng.configuration.client.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper客户端
 * @author HAO.ZUO
 * @date 2020/4/10--18:10
 */
@Slf4j
@Configuration
public class ZookeeperConfig {

    private static String zookeeperAddress = "47.98.133.16:10001";

    private static int timeout = 4000;

    public static ZooKeeper zkClient(){
        ZooKeeper zooKeeper=null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(zookeeperAddress, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(Event.KeeperState.SyncConnected==event.getState()){
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.info("【初始化ZooKeeper连接状态....】={}",zooKeeper.getState());
        }catch (Exception e){
            log.error("初始化ZooKeeper连接异常....】={}",e);
        }
        return  zooKeeper;
    }

}
