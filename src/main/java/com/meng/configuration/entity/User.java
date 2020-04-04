package com.meng.configuration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO 用户
 * @Author: Hao.Zuo
 * @Date: 2020/1/17 13:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -5913966444220297898L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 有效状态
     */
    private Integer validStatus;
    /**
     * 创建时间
     */
    private Date createTime;
}
