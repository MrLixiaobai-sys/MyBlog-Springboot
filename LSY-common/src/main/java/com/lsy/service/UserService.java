package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.dto.*;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.User;

public interface UserService extends IService<User> {

    ResponseResult userInfo();

    //更新个人信息
    ResponseResult updateUserInfo(UserDTO userDTO);

    ResponseResult register(UserRegisterDTO userRegisterDTO);

    ResponseResult addUser(UserAddDTO userAddDTO);

    ResponseResult getUserInfo(Long id);

    ResponseResult updateUserInfoAdmin(UserUpdateDTO userUpdateDTO);

    ResponseResult listUser(UserListDTO userListDTO);
}
