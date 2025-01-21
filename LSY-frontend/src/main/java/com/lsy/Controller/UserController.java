package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.dto.UserDTO;
import com.lsy.domain.dto.UserRegisterDTO;
import com.lsy.domain.ResponseResult;
import com.lsy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    //查询个人信息
    @GetMapping("/userInfo")
    @SystempLog(businessName = "查询用户个人信息")
    @ApiOperation(value = "用户个人信息",notes = "查询用户个人信息")
    public ResponseResult userInfo(){
       return userService.userInfo();
    }

    //更新个人信息
    @PutMapping("/userInfo")
    @SystempLog(businessName = "更新用户个人信息")
    @ApiOperation(value = "更新用户个人信息",notes = "更新用户个人信息")
    @ApiImplicitParam(name = "userDTO", value = "用户信息", required = true, dataType = "UserDTO")
    public ResponseResult updateUserInfo(@RequestBody UserDTO userDTO){
        return userService.updateUserInfo(userDTO);
    }

    //用户注册
    @PostMapping("/register")
    @SystempLog(businessName = "用户注册")
    @ApiOperation(value = "用户注册",notes = "用户注册")
    @ApiImplicitParam(name = "userRegisterDTO", value = "用户注册信息", required = true, dataType = "UserRegisterDTO")
    public ResponseResult register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        return userService.register(userRegisterDTO);
    }

}
