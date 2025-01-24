package com.lsy.domain.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuByIdVo {
    //菜单ID@TableId
    private Long id;

    //菜单名称
    private String menuName;

    //父菜单ID
    private Long parentId;

    //显示顺序
    private Integer orderNum;

    //路由地址
    private String path;


    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;

    //菜单状态（0显示 1隐藏）
    private String visible;

    //菜单状态（0正常 1停用）
    private String status;

    //菜单图标
    private String icon;

    //(当传入数据为空是不返回该字段）
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String remark;
}
