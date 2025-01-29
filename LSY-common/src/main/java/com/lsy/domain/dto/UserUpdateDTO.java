package com.lsy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    //昵称
    private String nickName;

    private String userName;
    private String password;

    //邮箱
    private String email;


    //用户性别（0男，1女，2未知）

    private String sex;

    //账号状态
    private String status;

    //用户关联的用户id列表
    private List<String> roleIds;
}
