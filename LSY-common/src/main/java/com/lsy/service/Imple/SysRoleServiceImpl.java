package com.lsy.service.Imple;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.entity.SysRole;
import com.lsy.mapper.SysRoleMapper;
import com.lsy.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2025-01-22 11:18:50
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
