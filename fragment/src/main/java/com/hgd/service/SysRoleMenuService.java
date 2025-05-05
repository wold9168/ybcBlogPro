package com.hgd.service;

import com.hgd.pojo.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
* @createDate 2024-08-02 12:17:11
*/
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    List<Long> getcheckedKeys(Long id);
}
