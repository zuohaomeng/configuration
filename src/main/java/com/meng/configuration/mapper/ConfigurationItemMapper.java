package com.meng.configuration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.configuration.entity.ConfigurationItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author 梦醉
 * @date 2020/3/12--0:46
 */
@Mapper
public interface ConfigurationItemMapper extends BaseMapper<ConfigurationItem> {
    @Update("update configurationitem set new_value=#{item.newValue}," +
            "update_time=#{item.updateTime},update_name=#{item.updateName}," +
            "remark=#{item.remark},update_status=#{item.updateStatus} where id=#{item.id}")
    int update(@Param("item") ConfigurationItem item);

    @Update("update configurationitem set valid_status = 0 set where id = #{id}")
    int delete(@Param("id") Integer id);
}
