package com.lsy.domain.entity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2025-01-14 20:48:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category extends Model<Category> {

    @TableId
    private Long id;

//  分类名
    private String name;

//  父分类id，如果没有父分类为-1
    private Long pid;

//  描述
    private String description;

//  状态0:正常,1禁用
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

//  删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
