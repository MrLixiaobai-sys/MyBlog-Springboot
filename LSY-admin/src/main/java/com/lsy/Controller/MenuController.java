package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.MenuAddRoleVo;
import com.lsy.domain.Vo.MenuByIdVo;
import com.lsy.domain.Vo.MenuVo;
import com.lsy.domain.Vo.RoutersVo;
import com.lsy.domain.entity.SysMenu;
import com.lsy.mapper.SysMenuMapper;
import com.lsy.service.SysMenuService;
import com.lsy.utils.AuthGetUtils;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    //获取当前用户的权限菜单
    @GetMapping("/getInfo")
    public ResponseResult getMenuList(){
        return sysMenuService.getMenuInfo();
    }



    //动态路由管理（根据用户的权限返回不同的前端页面，拥有不同的功能菜单）
    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userId = AuthGetUtils.getCurrentUserId();
        List<MenuVo> menuVos  =sysMenuService.selectRouterTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menuVos));
    }

//    展示菜单列表,支持模糊查询
    @GetMapping("/list")
    public ResponseResult list(String status, String menuName){
        return sysMenuService.Pagelist(status, menuName);
    }

//    新增菜单
    @GetMapping("content/article")
    public ResponseResult addMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return ResponseResult.okResult();
    }

//    根据id查询菜单
    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable Long id){
        SysMenu sysMenu = sysMenuService.getById(id);
        MenuByIdVo menuByIdVo = BeanCopyUtils.copyBean(sysMenu, MenuByIdVo.class);
        return ResponseResult.okResult(menuByIdVo);
    }

//    更新菜单
    @PutMapping()
    public ResponseResult updateMenu(@RequestBody MenuByIdVo menuByIdVo){

        SysMenu sysMenu = BeanCopyUtils.copyBean(menuByIdVo, SysMenu.class);
        sysMenuService.updateById(sysMenu);
        return ResponseResult.okResult();
    }

//    删除菜单
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable Long menuId){
        sysMenuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    //获取菜单树
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        Long userId = AuthGetUtils.getCurrentUserId();
        List<MenuAddRoleVo> menuAddRoleVos = sysMenuService.selectRounterTreeAddRoleById(userId);
        return ResponseResult.okResult(menuAddRoleVos);
    }

    //加载对应角色菜单列表树
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getRoleMenuTree(@PathVariable("id") Long roleId){
        return sysMenuService.getMenuTreeByRoleId(roleId);
    }

}
