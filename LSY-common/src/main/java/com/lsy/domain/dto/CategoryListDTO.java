package com.lsy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDTO {

    private String name;
    private String status;
    private Integer pageNum;
    private Integer pageSize;
}
