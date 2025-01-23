package com.lsy.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCategoryVo {

    //    分类id
    private Integer id;

    //    分类姓名
    private String name;

    //    描述
    private String description;
}
