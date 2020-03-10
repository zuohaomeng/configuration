package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.Project;
import com.meng.configuration.mapper.ProjectMapper;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.util.ResponseModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
    public List<Project> selectAllProject(int page, int limit) {
        String limitSql = "limit " + (page - 1) * limit + ", " + limit;
        List<Project> projects = projectMapper.selectList(new LambdaQueryWrapper<Project>()
                .eq(Project::getValidStatus, 1)
                .last(limitSql));
        return projects;
    }

    @Override
    public int getCount() {

        int count = projectMapper.selectCount(new LambdaQueryWrapper<Project>()
                .eq(Project::getValidStatus, 1));
        return count;
    }

    @Override
    public Project selectById(Integer id) {
        return projectMapper.selectById(id);
    }

    @Override
    public ResponseModel add(Project project) {
        LambdaQueryWrapper<Project> lambdaQueryWrapper = new LambdaQueryWrapper<Project>()
                .eq(Project::getValidStatus, 1)
                .eq(Project::getProjectId, project.getProjectId());
        Project project1 = projectMapper.selectOne(lambdaQueryWrapper);
        if (project1 != null) {
            return ResponseModel.ERROR("项目唯一标识已存在！");
        }
        Project projectDo = Project.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .email(project.getEmail())
                .leaderName(project.getLeaderName())
                .remark(project.getRemark())
                .groupId(project.getGroupId())
                .validStatus(1)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        int result = projectMapper.insert(projectDo);
        if (result > 0) {
            return ResponseModel.SUCCESS("成功");
        }
        return ResponseModel.ERROR("添加失败");
    }

    @Override
    public Integer update(Project project) {
        Integer updateResult = projectMapper.updateProject(project);
        return updateResult;
    }

    @Override
    public Integer delete(Integer id) {
        Integer result = projectMapper.deleteProject(id);
        return result;
    }

    @Override
    public List<Project> searchByprojectName(String portion) {
        List<Project> projects = projectMapper.selectList(new LambdaQueryWrapper<Project>()
                .like(Project::getProjectName, portion)
                .eq(Project::getValidStatus, 1)
                .last("limit 0,10"));
        return projects;
    }


}
