package com.lsy.service.Imple;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.entity.SysRoleMenu;
import com.lsy.mapper.SysRoleMenuMapper;
import com.lsy.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2025-01-22 11:50:30
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
