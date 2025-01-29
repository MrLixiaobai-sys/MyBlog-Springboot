package com.lsy.domain.Vo;

import com.lsy.domain.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAlterVo {
    private UserInfoVo user;
    private List<SysRole> roles;
    private List<String> roleIds;

}
