package com.meng.configuration.service;

import com.meng.configuration.entity.ProjectGroup;

import java.util.List;

/**
 * @author 梦醉
 * @date 2020/1/21--14:44
 */
public interface ProjectGroupService {
    /**
     * 获取所有项目组
     * @return
     */
    List<ProjectGroup> selectAllProjectGroup();
}
