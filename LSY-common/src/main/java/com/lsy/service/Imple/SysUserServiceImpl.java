package com.lsy.service.Imple;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.entity.SysUser;
import com.lsy.mapper.SysUserMapper;
import com.lsy.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2025-01-22 11:18:37
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
