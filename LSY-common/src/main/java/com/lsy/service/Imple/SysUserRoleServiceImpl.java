package com.lsy.service.Imple;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.entity.SysUserRole;
import com.lsy.mapper.SysUserRoleMapper;
import com.lsy.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(SysUserRole)表服务实现类
 *
 * @author makejava
 * @since 2025-01-22 11:41:17
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
