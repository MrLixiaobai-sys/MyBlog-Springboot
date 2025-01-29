package com.lsy.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkListVo {

        //    网站地址
        private String address;

        //    网站描述
        private String description;

        private Integer id;

        //    logo图片地址
        private String logo;

        //    网站名
        private String name;

        private String status;
}
