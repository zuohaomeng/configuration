package com.meng.configuration.security.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HAO.ZUO
 * @date 2020/4/3--21:31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
