package com.lsy.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "更新用户信息dto")
public class UserDTO {

    //主键@TableId
    private Long id;

    //昵称
    private String nickName;

    //邮箱
    private String email;


    //用户性别（0男，1女，2未知）
    @ApiModelProperty(notes = "用户性别（0男，1女，2未知）")
    private String sex;

    //头像
    @ApiModelProperty(notes = "头像外链地址")
    private String avatar;
}
