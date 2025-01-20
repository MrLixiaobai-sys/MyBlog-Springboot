package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.DTO.UserDTO;
import com.lsy.domain.DTO.UserRegisterDTO;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.UserInfoVo;
import com.lsy.domain.entity.User;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.mapper.UserMapper;
import com.lsy.service.UserService;
import com.lsy.utils.AuthGetUtils;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImple extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    //加密存储
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        //1.获取userId
        Long userId = AuthGetUtils.getCurrentUserId();

        //2.根据userId查询user信息
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();

        userWrapper.eq(User::getId, userId);
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
        updateWrapper.eq("id", userDTO.getId());

        //根据被赋值的user(非空属性)进行更新对应id的用户信息
        userMapper.update(newUser, updateWrapper);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(UserRegisterDTO userRegisterDTO) {
//        1.验证注册信息是否合法(邮箱是否为空，密码是否为空，昵称是否为空)
//            使用validation的notNull注解即可

//        2.验证用户名,昵称,邮箱是否重复
        ExistDetect(userRegisterDTO);

//        3.用户密码加密
        String encodepassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodepassword);

        User user = new User();
        User newUser = BeanCopyUtils.copyBean(userRegisterDTO,user.getClass());
//        4.保存用户信息到数据库
        save(newUser);

        return ResponseResult.okResult();
    }

    //判断用户名,昵称,邮箱是否重复
    private void ExistDetect(UserRegisterDTO userRegisterDTO) {

        //检验用户名是否重复
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getUserName, userRegisterDTO.getUserName());
        if(count(queryWrapper1) > 0){
            throw new SystemException(BlogHttpCodeEnum.USERNAME_EXIST);
        }

        //检验昵称是否重复
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getNickName, userRegisterDTO.getNickName());
        if(count(queryWrapper1) > 0){
            throw new SystemException(BlogHttpCodeEnum.NICKNAME_EXIST);
        }
    }

    //验证用户注册信息是否合法
//    private boolean validateUserRegistration(UserRegisterDTO userRegisterDTO) {
//
//    }
}
