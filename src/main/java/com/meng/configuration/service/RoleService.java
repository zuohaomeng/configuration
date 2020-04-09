package com.meng.configuration.service;

import com.meng.configuration.entity.vo.UserRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/4/8--19:49
 */
public interface RoleService {
    /**
     * 添加相应的角色关系
     * @param roleId
     * @param userId
     * @return
     */
    int addRoleRelation(int roleId,int userId);

    /**
     * 根据用户id获取角色id
     * @param userId
     * @return
     */
    int getRoleByUserId(Integer userId);

    List<UserRoleVO> getRoleRelations(int page, int limit);

    Integer changeRole(@Param("id") Integer id, @Param("type") Integer type);
}
