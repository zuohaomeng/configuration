package com.meng.configuration.service;

import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.util.ResponseModel;

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

    /**
     * 根据主键查询
     * @return
     */
    ProjectGroup selectById(Integer id);

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    List<ProjectGroup> selectGroupBypage(int page,int limit);

    /**
     * 获取项目组数量
     * @return
     */
    int getCount();

    /**
     * 添加
     * @return
     */
    ResponseModel add(ProjectGroup projectGroup);
    /**
     * 更新
     * @return
     */
    int update(ProjectGroup projectGroup);
    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 查询
     * @param portion
     * @return
     */
    List<ProjectGroup> searchByprojectName(String portion);
}
