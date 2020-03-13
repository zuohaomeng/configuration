package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hao.ZUO
 * @date 2020/3/12--22:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationItemVo implements Serializable {

    private static final long serialVersionUID = 1398712469561234101L;

    /**
     * id
     */
    private Integer id;

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private String value;

    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private String status;
    /**
     * 修改人
     */
    private String updateName;

    /**
     * 修改时间
     */
    private String updateTime;
}
