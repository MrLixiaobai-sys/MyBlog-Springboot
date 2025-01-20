package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.DTO.UserDTO;
import com.lsy.domain.DTO.UserRegisterDTO;
import com.lsy.domain.ResponseResult;
import com.lsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //查询个人信息
    @GetMapping("/userInfo")
    @SystempLog(businessName = "查询用户个人信息")
    public ResponseResult userInfo(){
       return userService.userInfo();
    }

    //更新个人信息
    @PutMapping("/userInfo")
    @SystempLog(businessName = "更新用户个人信息")
    public ResponseResult updateUserInfo(@RequestBody UserDTO userDTO){
        return userService.updateUserInfo(userDTO);
    }

    //用户注册
    @PostMapping("/register")
    @SystempLog(businessName = "用户注册")
    public ResponseResult register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        return userService.register(userRegisterDTO);
    }

}
