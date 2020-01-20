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
    List<Project> selectAllProject();

    /**
     * 根据id查询项目
     * @param id
     * @return
     */
    Project selectById(Integer id);

    ResponseModel add(Project project);
}
