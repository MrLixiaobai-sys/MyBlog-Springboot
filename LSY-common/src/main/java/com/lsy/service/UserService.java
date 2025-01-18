package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult userInfo();
}
