package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.UserAddDTO;
import com.lsy.domain.dto.UserListDTO;
import com.lsy.domain.dto.UserUpdateDTO;
import com.lsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService UserService;

    //新增用户
    @PostMapping()
    public ResponseResult addUser(@RequestBody UserAddDTO userAddDTO){


        return UserService.addUser(userAddDTO);
    }

    //删除用户
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){

        return ResponseResult.okResult(UserService.removeById(id));
    }

    //根据id查询用户信息
    @GetMapping("/{id}")
    public ResponseResult getUserInfo(@PathVariable Long id){

        return UserService.getUserInfo(id);
    }

    //修改用户信息
    @PutMapping()
    public ResponseResult updateUser(@RequestBody UserUpdateDTO userUpdateDTO){

        return UserService.updateUserInfoAdmin(userUpdateDTO);
    }

    //分页查询用户列表(支持模糊查询）
    @PostMapping("/list")
    public ResponseResult listUser(@RequestBody UserListDTO userListDTO){

        return UserService.listUser(userListDTO);
    }
}
