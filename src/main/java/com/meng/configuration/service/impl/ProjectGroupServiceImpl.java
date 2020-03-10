package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.Project;
import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.mapper.ProjectGroupMapper;
import com.meng.configuration.service.ProjectGroupService;
import com.meng.configuration.util.ResponseModel;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 梦醉
 * @date 2020/1/21--14:44
 */
@Slf4j
@Service
public class ProjectGroupServiceImpl implements ProjectGroupService {

    @Resource
    private ProjectGroupMapper projectGroupMapper;

    @Override
    public List<ProjectGroup> selectAllProjectGroup() {
        return projectGroupMapper.selectList(new LambdaQueryWrapper<ProjectGroup>()
                .eq(ProjectGroup::getValidStatus, 1));
    }

    @Override
    public ProjectGroup selectById(Integer id) {
        return projectGroupMapper.selectById(id);
    }

    @Override
    public List<ProjectGroup> selectGroupBypage(int page, int limit) {
        String limitSql = "limit " + (page - 1) * limit + ", " + limit;
        List<ProjectGroup> projectGroups = projectGroupMapper.selectList(new LambdaQueryWrapper<ProjectGroup>()
                .eq(ProjectGroup::getValidStatus, 1)
                .last(limitSql));
        return projectGroups;
    }

    @Override
    public int getCount() {
        return projectGroupMapper.selectCount(new LambdaQueryWrapper<ProjectGroup>()
                .eq(ProjectGroup::getValidStatus, 1));
    }

    @Override
    public ResponseModel add(ProjectGroup projectGroup) {
        List<ProjectGroup> projectGroups = projectGroupMapper.selectList(new LambdaQueryWrapper<ProjectGroup>()
                .eq(ProjectGroup::getGroupName, projectGroup.getGroupName())
                .eq(ProjectGroup::getValidStatus, 1));
        if(projectGroups==null||projectGroups.size()>0){
            return ResponseModel.ERROR("该项目组已存在");
        }
        ProjectGroup projectGroupDo = ProjectGroup.builder()
                .groupName(projectGroup.getGroupName())
                .leaderName(projectGroup.getLeaderName())
                .email(projectGroup.getEmail())
                .remark(projectGroup.getRemark())
                .validStatus(projectGroup.getValidStatus())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        int insert = projectGroupMapper.insert(projectGroupDo);
        if(insert>0){
            return ResponseModel.SUCCESS("添加成功");
        }
        return ResponseModel.ERROR("添加失败");
    }

    @Override
    public int update(ProjectGroup projectGroup) {
        return projectGroupMapper.updateProjectGroup(projectGroup);
    }

    @Override
    public int delete(Integer id) {
        return projectGroupMapper.deleteProjectGroup(id);
    }

    @Override
    public List<ProjectGroup> searchByprojectName(String portion) {
        List<ProjectGroup> projects = projectGroupMapper.selectList(new LambdaQueryWrapper<ProjectGroup>()
                .like(ProjectGroup::getGroupName, portion)
                .eq(ProjectGroup::getValidStatus, 1)
                .last("limit 0,10"));
        return projects;
    }
}
