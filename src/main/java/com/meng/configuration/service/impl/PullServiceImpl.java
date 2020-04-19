package com.meng.configuration.service.impl;

import cn.hutool.json.JSONUtil;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.PullService;
import com.meng.configuration.util.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class PullServiceImpl implements PullService {
    private static ConcurrentHashMap versionMap = new ConcurrentHashMap(64);

    private static ConcurrentHashMap<String, HashSet<Condition>> conditionMap = new ConcurrentHashMap(64);
    @Resource
    private ConfigurationItemService configurationItemService;


    @Override
    public String getAllItem(Integer projectId, Integer envId, Integer version) {
        Map itemS = configurationItemService.getAllItem(projectId, envId);
        return JSONUtil.toJsonStr(HttpResult.SUCCESS(itemS, version));
    }

    @Override
    public String cycleGetAllItem(Integer projectId, Integer envId, Integer version) {
        String ver = projectId + "+" + envId;
        if (!versionMap.containsKey(ver)) {
            versionMap.put(ver, 1);
            String itemS = getAllItem(projectId, envId, 1);
            return itemS;
        }
        //如果版本号不同
        if ((int) versionMap.get(ver) != version) {
            Map itemS = configurationItemService.getAllItem(projectId, envId);
            versionMap.put(ver, (Integer)versionMap.get(ver)+1);
            return JSONUtil.toJsonStr(HttpResult.SUCCESS(itemS, (int) versionMap.get(ver)));
        }
        //如果版本号相同，就等待
        String result = await(projectId, envId, version);

        return result;
    }


    public String await(Integer projectId, Integer envId, Integer version) {
        String ver = projectId+ "+" + envId;
        Lock lock = new ReentrantLock();
        lock.lock();
        Condition condition = lock.newCondition();
        HashSet<Condition> conditions = conditionMap.get(ver);
        try {
            //添加
            if(conditions !=null){
                conditions.add(condition);
            }else{
                conditions = new HashSet<>();
                conditions.add(condition);
                conditionMap.put(ver, conditions);
            }

            boolean await = condition.await(1, TimeUnit.MINUTES);
            //如果唤醒:有更新
            if (await) {
                log.info("[await],await={}", await);
                Map itemS = configurationItemService.getAllItem(projectId, envId);
                versionMap.put(ver, (int)versionMap.get(ver)+1);
                return JSONUtil.toJsonStr(HttpResult.SUCCESS(itemS, (int) versionMap.get(ver)));
            } else {//如果超时
                log.info("[await],await={}", await);
                return JSONUtil.toJsonStr(HttpResult.NOCHANGE());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            conditions.remove(ver);
            lock.unlock();
        }
        return JSONUtil.toJsonStr(HttpResult.ERROR("出现异常"));
    }

    /**
     * 唤醒操作
     *  1.如果versionMap没有，说明从来没有连接过，不用管
     *  2.如果versionMap有，如果连接过，就需要修改。
     * @param projectId
     * @param envId
     * @param version
     */
    @Override
    public void itemChange(Integer projectId, Integer envId, Integer version) {
        String ver = projectId+"+"+envId;
        if(versionMap.containsKey(ver)){
            Integer v = (Integer)versionMap.get(ver);
            versionMap.put(ver, v+1);
        }
        if(conditionMap.containsKey(ver)){
            HashSet<Condition> conditions = conditionMap.get(ver);
            for (Condition c:conditions) {
                c.signalAll();
            }
        }
    }
}
