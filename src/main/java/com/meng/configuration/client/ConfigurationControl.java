package com.meng.configuration.client;

import cn.hutool.json.JSONUtil;
import com.meng.configuration.entity.AddressNode;
import com.meng.configuration.register.AddressNodeService;
import com.meng.configuration.util.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 获取配置中心连接节点
 *
 * @author HAO.ZUO
 * @date 2020/4/11--19:27
 */
@Slf4j
public class ConfigurationControl {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private ZooKeeper zkClient = ZookeeperService.zkClient();
    private String path = "/configuration/address";
    private Integer version = 0;

    private ConfigMap configMap = new ConfigMap();
    private String env = "dev";
    private String projectId = "dubbo_add";

    public ConfigurationControl() {
    }

    public ConfigurationControl(ConfigMap configMap, String env, String projectId) {
        this.configMap = configMap;
        this.env = env;
        this.projectId = projectId;
    }

    public ConfigMap getConfigMap() {
        return configMap;
    }

    public void setConfigMap(ConfigMap configMap) {
        this.configMap = configMap;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void init() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //每5分钟拉去一次配置中心的服务器地址
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pullAddress();
                countDownLatch.countDown();
            }
        }, 0, 5, TimeUnit.MINUTES);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //每5分钟拉去一次配置
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List list = AddressNodeService.getList();
                int random = new Random().nextInt(list.size());
                if (list != null && list.size() > 0) {
                    HttpResult result = HttpResult.ERROR("连接错误");
                    try {
                        result = getAllItem((AddressNode) list.get(random), "getallitem");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    log.info("[getAddItem result],result={}", result.toString());
                    log.info("[cyclegetallitem code],code={}",result.getCode());
                    //说明有变化
                    if (result.getCode() == 0) {
                        version = result.getVersion();
                        configMap.setMap(result.getData());
                        configMap.updateItem();
                    } else if (result.getCode() == -1) {
                        throw new RuntimeException(result.getMsg());
                    }

                }
            }
        }, 2, 2, TimeUnit.MINUTES);

        //长轮询
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List list = AddressNodeService.getList();
                    int random = new Random().nextInt(list.size());
                    if (list != null && list.size() > 0) {
                        HttpResult result = HttpResult.ERROR("连接错误");
                        try {
                            result = getAllItem((AddressNode) list.get(random), "cyclegetallitem");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        log.info("[cyclegetallitem result],result={}", result);
                        //说明有变化
                        log.info("[cyclegetallitem code],code={}",result.getCode());
                        if (result.getCode() == 0) {
                            version = result.getVersion();
                            configMap.setMap(result.getData());
                            configMap.updateItem();
                        } else if (result.getCode() == -1) {
                            throw new RuntimeException(result.getMsg());
                        }

                    }
                }
            }
        });
    }

    private void pullAddress() {
        try {
            ArrayList<AddressNode> list = new ArrayList<>();
            List<String> children = zkClient.getChildren(path, false);
            for (int i = 0; i < children.size(); i++) {
                byte[] data = zkClient.getData(path + "/" + children.get(i), true, new Stat());
                String s = new String(data);
                String[] split = s.split(":");

                AddressNode address = new AddressNode(split[0], split[1]);
                list.add(address);

                AddressNodeService.change(list);
            }
        } catch (Exception e) {
            log.error("[error,{}]", e);
        }
    }

    public void start()  {
        init();
    }

    public HttpResult getAllItem(AddressNode addressNode, String url) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://" + addressNode.getAddress() + ":" + addressNode.getPort() +
                "/" + url +
                "?env=" + env +
                "&project=" + projectId +
                "&version=" + version);
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(600000).setConnectionRequestTimeout(600000)
                .setSocketTimeout(600000).build();
        httpGet.setConfig(requestConfig);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String s = EntityUtils.toString(responseEntity);
                HttpResult result = JSONUtil.toBean(s, HttpResult.class);
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        new ConfigurationControl().start();
    }


}
