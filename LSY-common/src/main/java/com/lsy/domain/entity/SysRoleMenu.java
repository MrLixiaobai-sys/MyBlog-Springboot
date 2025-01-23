package com.lsy.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联表(SysRoleMenu)表实体类
 *
 * @author makejava
 * @since 2025-01-22 11:50:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenu {
//角色ID@TableId
private Long roleId;
//菜单ID@TableId
private Long menuId;
}
