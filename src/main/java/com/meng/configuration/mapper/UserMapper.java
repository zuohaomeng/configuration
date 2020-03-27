package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 15:17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select user_number,name,email,valid_status,create_time from user where user_number = #{userNumber} and valid_status = 1")
    User hasUserByUserNumber(@Param("userNumber") String userNumber);
}
