package com.lsy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkAddDTO {

    //    网站地址
    private String address;

    //    网站描述
    private String description;



    //    logo图片地址
    private String logo;

    //    网站名
    private String name;

    private String status;
}
