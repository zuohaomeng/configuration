package com.meng.configuration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.mapper.ProjectGroupMapper;
import com.meng.configuration.service.ProjectGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 梦醉
 * @date 2020/1/21--14:44
 */
@Service
public class ProjectGroupServiceImpl implements ProjectGroupService {

    @Resource
    private ProjectGroupMapper projectGroupMapper;

    @Override
    public List<ProjectGroup> selectAllProjectGroup() {
        return projectGroupMapper.selectList(new LambdaQueryWrapper<ProjectGroup>()
                .eq(ProjectGroup::getValidStatus, 1));
    }
}
