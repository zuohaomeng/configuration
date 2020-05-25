package com.meng.configuration.client;

import cn.hutool.json.JSONUtil;
import com.meng.configuration.entity.AddressNode;
import com.meng.configuration.register.AddressNodeService;
import com.meng.configuration.util.HttpResult;
import lombok.Getter;
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
@Getter
@Slf4j
public class ConfigManager {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    private ZooKeeper zkClient = ZookeeperService.zkClient();
    private String path = "/configuration/address";
    private Integer version = 0;
    /**
     * 保存配置信息
     */
    private ConfigMap configMap = new ConfigMap();
    private String env = "dev";
    private String projectId = "dubbo_add";

    public ConfigManager() {
    }

    public ConfigManager(ConfigMap configMap, String env, String projectId) {
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
        //1.获取配置中心地址
        pullAddress();
        //每5分钟拉去一次配置中心的服务器地址
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pullAddress();
            }
        }, 3, 3, TimeUnit.MINUTES);

        //每5分钟拉去一次配置
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("定时任务获取");
                    List list = AddressNodeService.getList();
                    int random = new Random().nextInt(list.size());
                    if (list != null && list.size() > 0) {
                        HttpResult result = HttpResult.ERROR("连接错误");
                        try {
                            result = getAllItem((AddressNode) list.get(random), "getallitem");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        log.info("[getAddItem result],定时任务返回结果,result={}", result.toString());
                        log.info("[cyclegetallitem code],定时任务返回结果码,code={}", result.getCode());
                        //说明有变化
                        if (result.getCode() == 0) {
                            version = result.getVersion();
                            configMap.setMap(result.getData());
                            configMap.updateItem();
                        } else if (result.getCode() == -1) {
                            throw new RuntimeException(result.getMsg());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 2, 2, TimeUnit.MINUTES);

        //长轮询
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        List list = AddressNodeService.getList();
                        log.info("长轮询获取，list.size={}", list.size());
                        int random = new Random().nextInt(list.size());
                        if (list != null && list.size() > 0) {
                            HttpResult result = HttpResult.ERROR("连接错误");
                            try {
                                result = getAllItem((AddressNode) list.get(random), "cyclegetallitem");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            log.info("[cyclegetallitem result],长轮询返回结果,result={}", result);
                            //说明有变化
                            log.info("[cyclegetallitem code],长轮询返回状态,code={}", result.getCode());
                            if (result.getCode() == 0) {
                                version = result.getVersion();
                                configMap.setMap(result.getData());
                                configMap.updateItem();
                            } else if (result.getCode() == -1) {
                                throw new RuntimeException(result.getMsg());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 获取zookeeper中的地址信息
     */
    private void pullAddress() {
        try {
            ArrayList<AddressNode> list = new ArrayList<>();
            //1.获取所有子节点
            List<String> children = zkClient.getChildren(path, false);
            for (int i = 0; i < children.size(); i++) {
                byte[] data = zkClient.getData(path + "/" + children.get(i), true, new Stat());
                String s = new String(data);
                String[] split = s.split(":");
                AddressNode address = new AddressNode(split[0], split[1]);
                list.add(address);
                System.out.println("地址：" + address.getAddress() + "\t端口号：" + address.getPort());
            }
            AddressNodeService.change(list);
        } catch (Exception e) {
            log.error("[error,{}]", e);
        }
    }

    public void start() {
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
                .setConnectTimeout(120000).setConnectionRequestTimeout(120000)
                .setSocketTimeout(120000).build();
        httpGet.setConfig(requestConfig);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String s = EntityUtils.toString(responseEntity);
                HttpResult result = JSONUtil.toBean(s, HttpResult.class);
                return result;
            }
        } catch (Exception e) {
            pullAddress();
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


}
