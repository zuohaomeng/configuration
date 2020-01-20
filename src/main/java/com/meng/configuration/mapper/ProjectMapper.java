package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: TODO  项目相关
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 15:40
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}
