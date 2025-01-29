package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.MenuAddRoleVo;
import com.lsy.domain.Vo.MenuVo;
import com.lsy.domain.entity.SysMenu;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2025-01-22 11:14:37
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    //查询出管理员（所有的菜单及子菜单）
    List<MenuVo> selectAllRouterTree();

    //根据用户id查询出所有的菜单和子菜单
    List<MenuVo> selectRouterTreeByUserId(Long userId);

    List<MenuVo> selectChildrenRounterByParentId(Long id);

    List<String> selectPermsByUserId(Long id);

    List<String> selectRounterTreeAddRoleById(Long userId);

    List<MenuVo> selectRounterTreeByRoleId(Long roleId);
}
