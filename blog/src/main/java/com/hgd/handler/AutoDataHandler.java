package com.hgd.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hgd.security.UserDetailsImpl;
import com.hgd.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoDataHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
        UserDetailsImpl authUser = SecurityUtil.getAuthUser();
        if (authUser == null) {
            metaObject.setValue("updateBy", -1);
            metaObject.setValue("createBy", -1);
        } else {
            metaObject.setValue("updateBy", authUser.getSysUser().getId());
            metaObject.setValue("createBy", authUser.getSysUser().getId());
        }
        metaObject.setValue("updateTime", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", new Date());
        UserDetailsImpl authUser = SecurityUtil.getAuthUser();
        if (authUser == null) {
            metaObject.setValue("updateBy", -1);
        } else {
            metaObject.setValue("updateBy", authUser.getSysUser().getId());
        }
    }
}
