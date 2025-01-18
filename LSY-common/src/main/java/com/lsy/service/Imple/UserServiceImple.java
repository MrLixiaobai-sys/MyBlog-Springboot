package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.UserInfoVo;
import com.lsy.domain.entity.User;
import com.lsy.mapper.UserMapper;
import com.lsy.service.UserService;
import com.lsy.utils.AuthUtils;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImple extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //1.获取userId
        Long userId = AuthUtils.getCurrentUserId();

        //2.根据userId查询user信息
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();

        userWrapper.eq(User::getId,userId);
        User user = baseMapper.selectOne(userWrapper);

        //3.封装为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }
}
