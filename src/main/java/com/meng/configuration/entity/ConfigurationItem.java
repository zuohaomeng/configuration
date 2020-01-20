package com.meng.configuration.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: TODO 配置项
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 17:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationItem implements Serializable {

    private static final long serialVersionUID = 4997754470334747495L;
    private String id;
}
