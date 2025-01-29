package com.lsy.domain.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class MenuByRoleId {

    //子菜单
    private List<MenuAddRoleVo> menus = new ArrayList<>();

    //菜单id列表
    private List<String> checkedKeys;
}
