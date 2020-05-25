package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.entity.vo.GroupUserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 梦醉
 * @date 2020/1/21--14:46
 */
@Mapper
public interface ProjectGroupMapper extends BaseMapper<ProjectGroup> {
    /**
     * 修改
     *
     * @param projectgroup
     * @return
     */
    @Update("update projectgroup set group_name=#{projectgroup.groupName}," +
            "leader_name=#{projectgroup.leaderName},remark=#{projectgroup.remark},email=#{projectgroup.email}" +
            "where id = #{projectgroup.id}")
    Integer updateProjectGroup(@Param("projectgroup") ProjectGroup projectgroup);


    /**
     * 删除，修改有效位为0.
     * @param id
     * @return
     */
    @Update("update projectgroup set valid_status=0 where id = #{id}")
    Integer deleteProjectGroup(@Param("id") Integer id);

    @Insert("insert into group_user(group_id,user_id) value (#{groupid},#{userid})")
    int addGroupUser(Integer groupid, Integer userid);

    @Update("delete from group_user where group_id=#{groupid} and user_id=#{userid}")
    int deleteGroupUser(Integer groupid, Integer userid);
}
