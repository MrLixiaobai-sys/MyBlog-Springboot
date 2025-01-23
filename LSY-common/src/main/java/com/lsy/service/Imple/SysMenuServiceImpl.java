package com.lsy.service.Imple;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.MenuInfoVo;
import com.lsy.domain.Vo.MenuVo;
import com.lsy.domain.Vo.UserInfoVo;
import com.lsy.domain.entity.*;
import com.lsy.mapper.*;
import com.lsy.service.SysMenuService;
import com.lsy.utils.AuthGetUtils;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2025-01-22 11:14:40
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public ResponseResult getMenuInfo() {

//        1.查询出user用户返回信息：
//          （1）获取当前用户id
        Long userId = AuthGetUtils.getCurrentUserId();
//          （2）根据当前用户id查询用户信息(封装为响应格式)
        SysUser sysUser = sysUserMapper.selectById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(sysUser, UserInfoVo.class);

        //判断是否为管理员，如果为管理员直接返回查询出所有角色key为admin，permissions中所有菜单类型为C或者F的结果即可
        if (userId==1L){
            return getAdminMenuList(userInfoVo);
        }

//        2.查询出roles列表
//            (1)根据用户id查询出角色id
        SysUserRole sysUserRole = sysUserRoleMapper.selectById(userId);
        Long roleId = sysUserRoleMapper.selectOne(
                new QueryWrapper<SysUserRole>().eq("user_id", userId)
        ).getRoleId();

//            (2)根据roleId查询roleKey

        String roleKey = sysRoleMapper.selectById(roleId).getRoleKey();
        List<String> roleKeys =new ArrayList<>();
        roleKeys.add(roleKey);
//        3.查询出权限菜单列表
//            (1)根据roleId查询出menuId(多个）
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(queryWrapper);
//              提取出menuIds
        List<Long> menuIds = roleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());

//            (2)根据menuIds查询出permissions
        List<SysMenu> sysMenus = sysMenuMapper.selectBatchIds(menuIds);
        List<String> permissions = sysMenus.stream()
                .map(SysMenu::getPerms)
                .collect(Collectors.toList());

//        4.封装为响应格式
        MenuInfoVo menuInfoVo = new MenuInfoVo(userInfoVo, permissions, roleKeys);

        return ResponseResult.okResult(menuInfoVo);
    }

    //实现动态路由，根据当前用户id返回不同的菜单列表
    @Override
    public List<MenuVo> selectRouterTreeByUserId(Long userId) {

        List<MenuVo> menuVos = null;
//        判断是否为管理员
//          管理员则查询出所有菜单已经子菜单
//          否则根据当前用户id查询出菜单列表
        if(AuthGetUtils.isAdmin()){
            menuVos = sysMenuMapper.selectAllRouterTree();
        }else {
            menuVos = sysMenuMapper.selectRouterTreeByUserId(userId);
        }
//        对返回的menuVos进行children进行遍历赋值（子菜单也是一样的List<MenuVo>类型）
        for (MenuVo menuVo : menuVos) {
            List<MenuVo> children = sysMenuMapper.selectChildrenRounterByParentId(menuVo.getId());
            menuVo.setChildren(children);
        }
        return menuVos;
    }

    //    返回管理员的菜单列表
    public ResponseResult getAdminMenuList(UserInfoVo userInfoVo){

        List<String> roles = new ArrayList<>();
        roles.add("admin");
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getMenuType, "C", "F");
        queryWrapper.eq(SysMenu::getStatus, "0");
        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
        List<String> permissions = sysMenus.stream()
                .map(SysMenu::getPerms)
                .collect(Collectors.toList());
        return ResponseResult.okResult(new MenuInfoVo(userInfoVo, permissions, roles));
    }
}
