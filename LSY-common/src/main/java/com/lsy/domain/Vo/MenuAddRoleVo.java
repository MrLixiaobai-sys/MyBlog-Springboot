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
public class MenuAddRoleVo {
    private Long id;

    //父菜单ID
    private Long parentId;

    //菜单名称
    private String label;

    //子菜单
    private List<MenuAddRoleVo> children = new ArrayList<>();

}
