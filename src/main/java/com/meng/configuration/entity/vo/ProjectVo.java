package com.meng.configuration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目视图
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/20 16:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectVo implements Serializable {
    private static final long serialVersionUID = -6995514665292547643L;
    private Integer id;
}
