package com.hgd.service;

import com.hgd.pojo.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hgd.util.Result;
import com.hgd.vo.MenuTreeVo;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2024-08-02 12:17:11
*/
public interface SysMenuService extends IService<SysMenu> {

    Result menuList(String status, String menuName);

    Result addMenu(SysMenu sysMenu);

    Result getMenuById(int id);

    Result putMenu(SysMenu sysMenu);

    Result deleteMenu(int menuId);

    Result treeSelect();

    List<MenuTreeVo> getMenuTreeVos();
}
