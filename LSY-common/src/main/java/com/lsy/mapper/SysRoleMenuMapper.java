package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2025-01-22 11:50:28
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysRoleMenu> selectAdminMenuIds();
}
