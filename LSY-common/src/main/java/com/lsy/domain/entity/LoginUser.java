package com.lsy.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {



    /**
     * UserDetails 包含了用户的基本信息，如用户名、密码、权限等。
     * getUsername(): 获取用户名。
     * getPassword(): 获取密码。
     * getAuthorities(): 获取用户的权限或角色。
     * isAccountNonExpired(): 检查账户是否过期。
     * isAccountNonLocked(): 检查账户是否被锁定。
     * isCredentialsNonExpired(): 检查凭证（密码）是否过期。
     * isEnabled(): 检查账户是否启用
     */


    private User user;

    private List<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUserName();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
