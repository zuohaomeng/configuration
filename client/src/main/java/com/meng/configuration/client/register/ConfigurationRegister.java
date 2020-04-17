package com.meng.configuration.client.register;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 获取配置中心连接节点
 *
 * @author HAO.ZUO
 * @date 2020/4/11--19:27
 */
public class ConfigurationRegister {

    private ZooKeeper zkClient = ZookeeperConfig.zkClient();

    private String path = "/configuration";



    public void pull() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren(path, true);
        for (int i = 0; i < children.size(); i++) {
            System.out.println(children.get(i));
        }
        int index = new Random().nextInt(children.size());
        byte[] data = zkClient.getData(path + "/" + children.get(index), true, new Stat());
        System.out.println(new String(data));
    }

    public void getAllItem(){
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://localhost:12345/doGetControllerOne");

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
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
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

    }

}
