package com.lsy.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.MenuAddRoleVo;
import com.lsy.domain.Vo.MenuVo;
import com.lsy.domain.entity.SysMenu;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2025-01-22 11:14:39
 */
public interface SysMenuService extends IService<SysMenu> {
    

    ResponseResult Pagelist(String status, String menuName);

    ResponseResult getMenuInfo();

    List<MenuVo> selectRouterTreeByUserId(Long userId);

    List<MenuAddRoleVo> selectRounterTreeAddRoleById(Long userId);

    ResponseResult getMenuTreeByRoleId(Long roleId);
}
