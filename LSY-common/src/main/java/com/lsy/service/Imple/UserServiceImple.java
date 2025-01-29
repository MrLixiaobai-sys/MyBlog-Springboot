package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Vo.PageVo;
import com.lsy.domain.Vo.UserAlterVo;
import com.lsy.domain.Vo.UserListVo;
import com.lsy.domain.dto.*;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.UserInfoVo;
import com.lsy.domain.entity.SysRole;
import com.lsy.domain.entity.SysUserRole;
import com.lsy.domain.entity.User;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.mapper.SysRoleMapper;
import com.lsy.mapper.SysUserRoleMapper;
import com.lsy.mapper.UserMapper;
import com.lsy.service.SysUserRoleService;
import com.lsy.service.UserService;
import com.lsy.utils.AuthGetUtils;
import com.lsy.utils.BeanCopyUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImple extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //加密存储
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

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


    /**
     * 新增用户
     * 包括与角色的中间表也要新增
     * @param userAddDTO
     * @return
     */
    @Override
    public ResponseResult addUser(UserAddDTO userAddDTO) {

        //1.判断用户名,昵称,邮箱是否重复
        ExistDetect(BeanCopyUtils.copyBean(userAddDTO, UserRegisterDTO.class));

        //新增用户表
        User user = BeanCopyUtils.copyBean(userAddDTO, User.class);

        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        Long userId = user.getId();
        // 3. 处理用户角色绑定（roleIds 不能为空）
        if (!CollectionUtils.isEmpty(userAddDTO.getRoleIds())) {
            // **将 roleIds 转换为 SysUserRole 实体列表**
            List<SysUserRole> userRoles = userAddDTO.getRoleIds().stream()
                    .map(roleId -> new SysUserRole(userId, Long.valueOf(roleId))) // userId 关联多个 roleId
                    .collect(Collectors.toList());

            // **批量插入中间表**
            sysUserRoleService.saveBatch(userRoles);
        }

        return ResponseResult.okResult();
    }

    /*
        根据id获取用户信息
        Path格式请求参数：
        id: 用户id
        响应格式：
        roleIds：用户所关联的角色id列表
        roles：所有角色的列表
        user：用户信息
     */

    @Override
    public ResponseResult getUserInfo(Long id) {

//        1.根据id查询用户信息
        User user = userMapper.selectById(id);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

//        2.根据用户id查询用户所关联的角色id列表
        List<String> roleIds = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id))
                .stream()
                .map(role -> String.valueOf(role.getRoleId())) // 将 Long 转为 String
                .collect(Collectors.toList());


//        3.根据角色id列表查询所有角色信息

            List<SysRole> roles = sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, roleIds));

//        4.封装为响应格式
        UserAlterVo userAlterVo = new UserAlterVo(userInfoVo, roles, roleIds);
        return ResponseResult.okResult(userAlterVo);
    }

    //后台更新用户信息
    @Override
    public ResponseResult updateUserInfoAdmin(UserUpdateDTO userUpdateDTO) {
        User user = BeanCopyUtils.copyBean(userUpdateDTO, User.class);
        updateById(user);
        return ResponseResult.okResult();
    }

    //后台用户列表分页查询(模糊查询）
    @Override
    public ResponseResult listUser(UserListDTO userListDTO) {

//        1.封装查询条件
//        模糊查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(userListDTO.getUserName()), User::getUserName, userListDTO.getUserName());
        queryWrapper.eq(StringUtils.hasText(userListDTO.getPhonenumber()), User::getNickName, userListDTO.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDTO.getStatus()), User::getStatus, userListDTO.getStatus());

        Page<User> page = new Page<>();
        page(page, queryWrapper);
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        return ResponseResult.okResult(new PageVo(userListVos, page.getTotal()));
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
