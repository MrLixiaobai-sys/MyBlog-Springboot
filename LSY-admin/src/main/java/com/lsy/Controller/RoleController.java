package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.RoleStatusDTO;
import com.lsy.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleStatus;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

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


}
