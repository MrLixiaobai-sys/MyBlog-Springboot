package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.DTO.UserDTO;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.UserInfoVo;
import com.lsy.domain.entity.User;
import com.lsy.mapper.UserMapper;
import com.lsy.service.UserService;
import com.lsy.utils.AuthGetUtils;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImple extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult userInfo() {
        //1.获取userId
        Long userId = AuthGetUtils.getCurrentUserId();

        //2.根据userId查询user信息
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();

        userWrapper.eq(User::getId,userId);
        User user = baseMapper.selectOne(userWrapper);

        //3.封装为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    //更新个人信息
    @Override
    public ResponseResult updateUserInfo(UserDTO userDTO) {


        //1.将传入的DTO非空属性赋值给新的user,DTO没有的属性user为空
        User user = new User();
        User newUser = BeanCopyUtils.copyBean(userDTO, user.getClass());

        //根据user的非空属性进行创建更新wrapper(null属性不会被创建查询条件)
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userDTO.getId());

        //根据被赋值的user(非空属性)进行更新对应id的用户信息
        userMapper.update(newUser,updateWrapper);

        return ResponseResult.okResult();
    }
}
