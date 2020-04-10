package com.meng.configuration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 发布历史
 * @author Hao.ZUO
 * @date 2020/3/12--22:27
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("releasehistory")
public class ReleaseHistory implements Serializable {

    private static final long serialVersionUID = -9008273294084691190L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 配置项id
     */
    private Integer itemId;
    /**
     * 修改对应的key
     */
    private String issueKey;

    /**
     * 旧的value
     */
    private String oldValue;

    /**
     * 新的value
     */
    private String newValue;

    /**
     * 发布的版本号
     */
    private Integer issueVersion;

    /**
     * 所属环境
     */
    private Integer env;

    /**
     * 修改人
     */
    private String updateName;

    /**
     * 更新时间
     */
    private Date updateTime;
}
