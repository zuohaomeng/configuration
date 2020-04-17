package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.ReleaseHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Hao.ZUO
 * @date 2020/3/12--23:24
 */
@Mapper
public interface ReleaseHistoryMapper extends BaseMapper<ReleaseHistory> {
}
