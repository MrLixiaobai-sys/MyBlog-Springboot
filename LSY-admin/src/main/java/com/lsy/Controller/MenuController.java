package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.MenuVo;
import com.lsy.domain.Vo.RoutersVo;
import com.lsy.domain.entity.SysMenu;
import com.lsy.service.SysMenuService;
import com.lsy.utils.AuthGetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

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
}
