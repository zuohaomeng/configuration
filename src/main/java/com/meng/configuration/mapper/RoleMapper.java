package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meng.configuration.entity.Role;
import com.meng.configuration.entity.vo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/4/8--19:49
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Update("insert into  user_role(user_id,role_id) value (#{userId},#{roleId})")
    Integer addRoleRelation(@Param("roleId") int roleId, @Param("userId") int userId);


    @Select("select role_id from user_role where user_id=#{userId}")
    Integer getRoleByUserId(@Param("userId") Integer userId);

    @Select("SELECT r.*, u.username,u.`name` FROM `user` AS u LEFT JOIN user_role AS r ON u.id = r.user_id")
    List<UserRoleVO> getRoleRelations(Page page);

    @Update("update user_role set role_id=#{type} where id =#{id}")
    Integer changeRole(@Param("id") Integer id, @Param("type") Integer type);
}
