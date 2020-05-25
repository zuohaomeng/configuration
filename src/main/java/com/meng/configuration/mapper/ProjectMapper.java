package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.Project;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description: TODO  项目相关
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:40
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    @Update("update project set project_id=#{project.projectId},project_name=#{project.projectName}," +
            "leader_name=#{project.leaderName},remark=#{project.remark},email=#{project.email}" +
            "where id = #{project.id}")
    Integer updateProject(@Param("project") Project project);

    @Update("update project set valid_status=0 where id = #{id}")
    Integer deleteProject(@Param("id") Integer id);

    @Update("update project set version=version+1 , update_time = now() where id =#{id}")
    Integer incrementVersion(Integer id);


    @Select("SELECT * from project as p WHERE p.group_id in (SELECT group_id FROM group_user WHERE user_id = #{userid}) AND valid_status =1 LIMIT #{start},#{limit}")
    List<Project> selectPageByUserId(int start, int limit, Integer userid);

    @Select("SELECT count(*) from project as p WHERE p.group_id in (SELECT group_id FROM group_user WHERE user_id = #{userid}) AND valid_status =1 ")
    int getCountByUserid(Integer userid);

    @Select("SELECT * from project as p WHERE p.group_id in (SELECT group_id FROM group_user WHERE user_id = #{userid}) and project_name LIKE '%${portion}%' AND valid_status =1 LIMIT 0,10")
    List<Project> searchByprojectName(String portion, Integer userid);
}
