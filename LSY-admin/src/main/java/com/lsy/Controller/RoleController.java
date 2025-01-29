package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.RoleAddDTO;
import com.lsy.domain.dto.RoleStatusDTO;
import com.lsy.domain.dto.RoleUpdateDTO;
import com.lsy.domain.entity.SysRole;
import com.lsy.mapper.SysRoleMapper;
import com.lsy.service.SysRoleService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleStatus;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMapper SysRoleMapper;

    //角色列表分页查询
    @GetMapping("/list")
    public ResponseResult list(String roleName, String status, Integer pageNum, Integer pageSize) {

        return sysRoleService.pageByCondition(roleName, status, pageNum, pageSize);
    }

    //改变角色的停启用状态
    @GetMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDTO roleStatusDTO) {
        return sysRoleService.changeStatus(roleStatusDTO);
    }

    //新增角色
    @PostMapping()
    public ResponseResult addRole(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return ResponseResult.okResult();
    }

    //角色回显(根据id查询角色）
    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable Long id) {
        SysRole sysRole = SysRoleMapper.selectById(id);
        return ResponseResult.okResult(sysRole);
    }

    //修改角色
   @PutMapping()
    public ResponseResult updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO) {
        SysRole sysRole = BeanCopyUtils.copyBean(roleUpdateDTO, SysRole.class);
        sysRoleService.updateById(sysRole);
        return ResponseResult.okResult();
    }

    //删除角色
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id) {
        sysRoleService.removeById(id);
        return ResponseResult.okResult();
    }


}
