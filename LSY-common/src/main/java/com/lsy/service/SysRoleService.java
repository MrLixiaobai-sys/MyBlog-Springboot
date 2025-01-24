package com.lsy.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.RoleStatusDTO;
import com.lsy.domain.entity.SysRole;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2025-01-22 11:18:50
 */
public interface SysRoleService extends IService<SysRole> {

    ResponseResult pageByCondition(String roleName, String status, Integer pageNum, Integer pageSize);

    ResponseResult changeStatus(RoleStatusDTO roleStatusDTO);
}
