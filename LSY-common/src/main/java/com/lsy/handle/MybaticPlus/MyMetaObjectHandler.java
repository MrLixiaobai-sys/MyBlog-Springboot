package com.lsy.handle.MybaticPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lsy.utils.AuthUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        // 获取当前登录用户ID并填充创建人,更新人
        Long userId = AuthUtils.getCurrentUserId();  // 获取当前登录用户的 ID
        // 自动填充创建时间
        this.setFieldValByName("createTime", new Date(), metaObject);

        // 自动填充更新时间
        this.setFieldValByName("updateTime", new Date(), metaObject);

        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateBy",userId,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充更新时间
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
