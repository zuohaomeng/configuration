package com.meng.configuration.service.impl;

import cn.hutool.json.JSONUtil;
import com.meng.configuration.register.LockAndCondition;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.PullService;
import com.meng.configuration.util.HttpResult;
import lombok.Getter;
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

@Getter
@Slf4j
@Service
public class PullServiceImpl implements PullService {
    public static ConcurrentHashMap versionMap = new ConcurrentHashMap(64);

    public static ConcurrentHashMap<String, HashSet<LockAndCondition>> conditionMap = new ConcurrentHashMap(64);
    @Resource
    private ConfigurationItemService configurationItemService;

    //获取所有配置项

    /**
     * 直接返回所有配置
     * @param projectId
     * @param envId
     * @param version
     * @return
     */
    @Override
    public String getAllItem(Integer projectId, Integer envId, Integer version) {
        Map itemS = configurationItemService.getAllItem(projectId, envId);
        return JSONUtil.toJsonStr(HttpResult.SUCCESS(itemS, version));
    }

    //长轮询获取
    /**
     * 1.如果第一次连接，versionMap添加当前连接项目,version = 1
     * 2.如果版本号不同，说明有修改，直接返回所有配置，version+1;
     * 3.如果版本号相同，进行等待；
     * @param projectId
     * @param envId
     * @param version
     * @return
     */
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
            log.info("[PullServiceImpl-cycleGetAllItem],版本号不同的更新,version={}",versionMap.get(ver));
            return JSONUtil.toJsonStr(HttpResult.SUCCESS(itemS, (Integer) versionMap.get(ver)));
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
        HashSet<LockAndCondition> conditions = conditionMap.get(ver);
        LockAndCondition lockAndCondition = new LockAndCondition(lock, condition);
        try {
            //添加
            if(conditions != null){
                conditions.add(lockAndCondition);
            }else{
                conditions = new HashSet<>();
                conditions.add(lockAndCondition);
                conditionMap.put(ver, conditions);
            }

            boolean await = condition.await(10, TimeUnit.SECONDS);
            //如果唤醒:有更新
            if (await) {
                Map itemS = configurationItemService.getAllItem(projectId, envId);
//                versionMap.put(ver, (int)versionMap.get(ver)+1);
                log.info("[PullServiceImpl-await],有更新唤醒,verison={}",version);
                return JSONUtil.toJsonStr(HttpResult.SUCCESS(itemS, (int) versionMap.get(ver)));
            } else {//如果超时
                log.info("[PullServiceImpl-await],无更新超时,version={}", version);
                return JSONUtil.toJsonStr(HttpResult.NOCHANGE());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            conditions.remove(lockAndCondition);
            lock.unlock();
        }
        return JSONUtil.toJsonStr(HttpResult.ERROR("出现异常"));
    }

    /**
     * 唤醒操作
     *  1.如果versionMap没有，说明从来没有连接过，不用管
     *  2.如果versionMap有，如果连接过，就需要修改。
     *      1.首相把版本号加一
     *      2.然后把所有等待的通知进行发送；
     * @param projectId
     * @param envId
     * @param version
     */
    @Override
    public void itemChange(Integer projectId, Integer envId, Integer version) {
        log.info("[PullServiceImpl-itemChange],有配置更新触发唤醒，projectId={},envId={},version={}",projectId,envId,version);
        String ver = projectId+"+"+envId;
        if(versionMap.containsKey(ver)){
            Integer v = (Integer)versionMap.get(ver);
            versionMap.put(ver, version);
            if(conditionMap.containsKey(ver)){
                HashSet<LockAndCondition> lockAndConditions = conditionMap.get(ver);
                for (LockAndCondition lc:lockAndConditions) {
                    try {
                        lc.getLock().lock();
                        lc.getCondition().signalAll();
                    }finally {
                        lc.getLock().unlock();
                    }
                }
            }
        }

    }
}
