package com.hgd.service;

import com.hgd.pojo.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service
* @createDate 2024-08-02 12:17:11
*/
public interface SysUserRoleService extends IService<SysUserRole> {

    List<Long> getRoleId(int id);
}
