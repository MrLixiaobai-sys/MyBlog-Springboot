package com.lsy.handle.MybaticPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lsy.utils.AuthGetUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {


        this.setFieldValByName("createTime", new Date(), metaObject);

        // 自动填充更新时间
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 获取当前登录用户ID并填充创建人,更新人

        // 自动填充创建时间
        // 尝试获取当前用户 ID，若失败则设置默认值
        Long userId;
        try {
            userId = AuthGetUtils.getCurrentUserId(); // 获取当前登录用户的 ID
            if (userId == null) {
                userId = -1L; // 默认值，表示系统用户或未登录用户
            }
        } catch (Exception e) {
            userId = -1L; // 捕获异常，设置为默认值
        }

        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateBy",userId,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充更新时间
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
