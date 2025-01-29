package com.lsy.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDTO {


    //昵称
    private String nickName;

    private String userName;
    private String password;
    private String phonenumber;

    //邮箱
    private String email;


    //用户性别（0男，1女，2未知）

    private String sex;

    //账号状态
    private String status;

    //用户关联的用户id列表
    private List<String> roleIds;


}
