package com.meng.configuration.service;


import com.meng.configuration.entity.Project;
import com.meng.configuration.util.ResponseModel;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:39
 */
public interface ProjectService {
    /**
     * 查询所有项目
     * @return
     */
    List<Project> selectAllProject(int page,int limit);

    /**
     * 获取有效数量
     * @return
     */
    int getCount();
    /**
     * 根据id查询项目
     * @param id
     * @return
     */
    Project selectById(Integer id);

    /**
     * 添加项目
     * @param project
     * @return
     */
    ResponseModel add(Project project);

    /**
     * 更新项目
     * @param project
     * @return
     */
    Integer update(Project project);

    /**
     * 删除项目
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 根据项目名搜索
     * @param portion
     * @return
     */
    List<Project> searchByprojectName(String portion);
}
