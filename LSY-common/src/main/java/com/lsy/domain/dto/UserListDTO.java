package com.lsy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO {


    private Integer pageNum;

    private Integer pageSize;

    // 用户名称
    private String userName;

    // 电话号码
    private String phonenumber;

    // 状态（0正常 1停用）
    private String status;

}
