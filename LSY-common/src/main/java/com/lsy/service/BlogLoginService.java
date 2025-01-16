package com.lsy.service;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);
}
