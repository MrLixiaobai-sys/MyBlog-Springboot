package com.lsy.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoVo {

//    当前用户信息
    private UserInfoVo user;

//    菜单权限
    private List<String> permissions;

//    角色权限字符
    private List<String> roles;



}
