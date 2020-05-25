package com.meng.configuration.register;

import com.meng.configuration.entity.AddressNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class AddressNodeService {

    //配置中心地址集合
    private static List<AddressNode> arrayList = new ArrayList();
    //读写锁
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public static void change(ArrayList list) {
        lock.writeLock().lock();
        try {
            arrayList = list;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public static List getList() {
        lock.readLock().lock();
        List<AddressNode> list = null;
        try {
           list  = arrayList;
        }finally {
            lock.readLock().unlock();
        }
        return list;
    }
}
