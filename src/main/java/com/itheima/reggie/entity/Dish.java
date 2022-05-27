package com.itheima.reggie.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜品管理
 * </p>
 *
 * @author WilsonEdwards
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 菜品分类id
     */
    private Long categoryId;

    /**
     * 菜品价格
     */
    private BigDecimal price;

    /**
     * 商品码
     */
    private String code;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 0 停售 1 起售
     */
    private Integer status;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入、更新时填充字段
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入、更新时填充字段
    private Long updateUser;

    /**
     * 是否删除
     */
    private Integer isDeleted;


}
