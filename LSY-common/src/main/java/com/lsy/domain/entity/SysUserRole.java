package com.lsy.domain.entity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户和角色关联表(SysUserRole)表实体类
 *
 * @author makejava
 * @since 2025-01-22 11:41:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class SysUserRole {
//用户ID@TableId
private Long userId;
//角色ID@TableId
private Long roleId;
}
