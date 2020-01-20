package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.Project;
import com.meng.configuration.mapper.ProjectMapper;
import com.meng.configuration.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO 项目相关
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:49
 */
@Service
public class ProjecServiceImpl implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

    @Override
    public List<Project> selectAllProject() {
        List<Project> projects = projectMapper.selectList(new LambdaQueryWrapper<Project>().eq(Project::getValidStatus, 1));
        return projects;
    }

    @Override
    public Project selectById(Integer id) {
        return projectMapper.selectById(id);
    }

}
