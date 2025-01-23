package com.lsy.service;

import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.utils.AuthGetUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service("ps")
public class PermissionService {
    /*** 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     * */
    public boolean hasPermission(String permission) {
//如果是超级管理员 直接返回true
        if (AuthGetUtils.isAdmin()) {
            return true;
        }
//否则 获取当前登录用户所具有的权限列表 如何判断是否存在permission

        List<String> permissions = AuthGetUtils.getCurrentUserDetails().getPermissions();
        if (Objects.isNull(permissions)){
            throw new SystemException(BlogHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        return permissions.contains(permission);
    }
}
