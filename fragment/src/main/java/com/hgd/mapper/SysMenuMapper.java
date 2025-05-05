package com.hgd.mapper;

import com.hgd.pojo.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgd.vo.MenuVo;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2024-08-02 12:17:11
* @Entity com.hgd.pojo.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    public List<String> getPermsByUserId(Long userId);

    public List<String> getPermsAll();

    public List<MenuVo> getMenusByUserId(Long userId);

    public List<MenuVo> getMenusAll();
}




