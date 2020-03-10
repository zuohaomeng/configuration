package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.Project;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Description: TODO  项目相关
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:40
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    @Update("update project set project_id=#{project.projectId},project_name=#{project.projectName}," +
            "leader_name=#{project.l" +
            "eaderName},remark=#{project.remark},email=#{project.email}" +
            "where id = #{project.id}")
    Integer updateProject(@Param("project") Project project);

    @Update("update project set valid_status=0 where id = #{id}")
    Integer deleteProject(@Param("id") Integer id);
}
