package com.lsy.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    //主键@TableId
    private Long id;

    //昵称
    private String nickName;

    //邮箱
    private String email;


    //用户性别（0男，1女，2未知）
    private String sex;

    //头像
    private String avatar;
}
