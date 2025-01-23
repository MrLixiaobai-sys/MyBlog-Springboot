package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsy.domain.entity.LoginUser;
import com.lsy.domain.entity.User;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.exception.UserNotFoundException;
import com.lsy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /*
        默认为到UserDetailsService里面从内存中查询是否有该用户
        重新建立他的实现类,这样验证用户就会跳到该实现类里面进行查询数据库里面是否有该用户
     */


    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) {
//根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        User user = userMapper.selectOne(queryWrapper);
//判断是否查到用户 如果没查到抛出异常
        if (Objects.isNull(user)) {
            throw new SystemException(BlogHttpCodeEnum.USER_NOT_EXIST);
        }
//返回用户信息
// TODO 查询权限信息封装

//        返回时调用LoginUser类中的isAccountNonLocked方法,isEnabled方法等方法
        return new LoginUser(user);
    }
}
