package com.meng.configuration.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO 菜单
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 14:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu implements Serializable {
    private static final long serialVersionUID = 2456469886979913037L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private String orderNum;
    /**
     * 请求地址
     */
    private String url;
    /**
     * target
     */
    private String target;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private Character menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    private Character visible;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 备注
     */
    private String remark;
    /**
     *  子菜单
     */
    @TableField(exist =false)
    private List<Menu> children = new ArrayList<Menu>();

}
