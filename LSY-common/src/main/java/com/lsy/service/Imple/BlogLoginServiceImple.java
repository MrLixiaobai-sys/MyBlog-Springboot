package com.lsy.service.Imple;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.BlogUserLoginVo;
import com.lsy.domain.Vo.UserInfoVo;
import com.lsy.domain.entity.LoginUser;
import com.lsy.domain.entity.User;
import com.lsy.service.BlogLoginService;
import com.lsy.utils.BeanCopyUtils;
import com.lsy.utils.JwtUtil;
import com.lsy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImple implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {


        /*
            1.封装认证凭证类
            通过对输入的username和password进行认证
            将用户输入的 username 和 password 封装成一个 UsernamePasswordAuthenticationToken 对象，这是 Spring Security 中标准的认证凭证类。
            这个对象会被传递到认证管理器中，用于后续的认证流程
         */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        /*
            2. 调用认证管理器进行认证
            调用 AuthenticationManager 的 authenticate 方法，启动认证流程。
            AuthenticationManager 会委托具体的 AuthenticationProvider 执行认证，默认情况下是 DaoAuthenticationProvider。
            DaoAuthenticationProvider 内部调用了你自定义的 UserDetailsServiceImpl来加载用户数据
            会跳转到UserDetailsServiceImpl进行查询数据库是否有该用户(默认会调用UserDetailsService类进行查询内存,这里重建一个UserDetailsService实现类)
         */

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

//        判断是否认证通过(不通过返回authenticate为null)
//            认证不通过
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("用户名或者密码不通过");
//        }
//            认证通过 
//        获取到userid并且生成token

        LoginUser loginUser  = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);

//        认证通过后的用户信息存入到redis,便于下次直接通过token认证
//        有缓存时直接通过JWT解析出userId，进行从缓存中查询loginUser即用户信息
//        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId)

        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

//        把token和userInfo封装

//        User转换为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(),UserInfoVo.class);

//        将最终token和UserInfoVo封装为BlogUserLoginVo响应格式
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token,userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}
