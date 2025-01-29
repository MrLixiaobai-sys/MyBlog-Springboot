package com.lsy.service.Imple;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.PageVo;
import com.lsy.domain.Vo.RoleVo;
import com.lsy.domain.dto.RoleStatusDTO;
import com.lsy.domain.entity.SysRole;
import com.lsy.mapper.SysRoleMapper;
import com.lsy.service.SysRoleService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2025-01-22 11:18:50
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleService sysRoleService;


    /*
         需要有角色列表分页查询的功能。
         要求能够针对角色名称进行模糊查询。
         要求能够针对状态进行查询。
         要求按照role_sort进行升序排列
     */
    @Override
    public ResponseResult pageByCondition(String roleName, String status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

        //动态条件查询
        queryWrapper.like(StringUtils.hasText(roleName), SysRole::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(status), SysRole::getStatus, status);
        queryWrapper.orderByAsc(SysRole::getRoleSort);

        //封装为分页对象
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<SysRole> records = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(records, RoleVo.class);
        return ResponseResult.okResult(new PageVo(roleVos, page.getTotal()));
    }

//    要求能够根据id修改角色的停启用状态
    @Override
    public ResponseResult changeStatus(RoleStatusDTO roleStatusDTO) {
        SysRole sysRole = BeanCopyUtils.copyBean(roleStatusDTO, SysRole.class);
        updateById(sysRole);
        return ResponseResult.okResult();
    }

    //    查询所有角色列表
    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

//        查询的是所有状态正常的角色
        queryWrapper.eq(SysRole::getStatus, "0");
        List<SysRole> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }
}
