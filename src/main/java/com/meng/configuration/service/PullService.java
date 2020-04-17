package com.meng.configuration.service;

public interface PullService {

    String getAllItem(Integer projectId, Integer envId,Integer version);

    String cycleGetAllItem(Integer projectId, Integer envId,Integer version);

    void itemChange(Integer projectId,Integer envId,Integer version);
}
