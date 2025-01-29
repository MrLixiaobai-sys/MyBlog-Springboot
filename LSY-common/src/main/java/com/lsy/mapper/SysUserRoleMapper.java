package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.entity.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2025-01-22 11:41:15
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void insertBatch(List<SysUserRole> userRoles);
}
