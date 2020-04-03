package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.security.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/3/31--13:37
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    @Select("SELECT " +
            "p.* " +
            "FROM " +
            "user AS u " +
            "LEFT JOIN user_role AS ur ON u.id = ur.user_id " +
            "LEFT JOIN role AS r ON r.id = ur.role_id " +
            "LEFT JOIN role_permission AS rp ON r.id = rp.role_id " +
            "LEFT JOIN permission AS p ON p.id = rp.permission_id " +
            "WHERE " +
            "u.id = #{userId}")
    List<Permission> selectByUserId(Long userId);
}
