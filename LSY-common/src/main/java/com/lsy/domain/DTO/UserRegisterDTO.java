package com.lsy.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @NotNull(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度应在3到50字符之间")
    private String userName;

    @NotNull(message = "密码不能为空")
    @Size(min = 3, max = 50, message = "密码长度应在3到50字符之间")
    private String password;

    @NotNull(message = "昵称不能为空")
    @Size(min = 3, max = 50, message = "昵称长度应在3到50字符之间")
    private String nickName;

    @NotNull(message = "邮箱不能为空")
    @Size(min = 6, max = 50, message = "邮箱长度应在3到50字符之间")
    private String email;
}
