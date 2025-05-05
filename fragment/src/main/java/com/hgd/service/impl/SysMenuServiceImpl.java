package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.http.HttpCode;
import com.hgd.pojo.SysMenu;
import com.hgd.service.SysMenuService;
import com.hgd.mapper.SysMenuMapper;
import com.hgd.util.MyCopyBeanUtil;
import com.hgd.util.Result;
import com.hgd.vo.MenuTreeVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author lenovo
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2024-08-02 12:17:11
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{
    @Override
    public Result menuList(String status, String menuName) {
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasText(status)){
            lambdaQueryWrapper.like(SysMenu::getStatus,status);
        }
        if(StringUtils.hasText(menuName)){
            lambdaQueryWrapper.like(SysMenu::getMenuName,menuName);
        }
        lambdaQueryWrapper.orderByAsc(SysMenu::getOrderNum);
        lambdaQueryWrapper.orderByAsc(SysMenu::getParentId);
        List<SysMenu> menuList = list(lambdaQueryWrapper);
        return Result.ok(menuList);
    }

    @Override
    public Result addMenu(SysMenu sysMenu) {
        save(sysMenu);
        return Result.ok();
    }

    @Override
    public Result getMenuById(int id) {
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysMenu::getId, id);
        return Result.ok(list(lambdaQueryWrapper).get(0));
    }

    @Override
    public Result putMenu(SysMenu sysMenu) {
        if(sysMenu.getId()==sysMenu.getParentId()){
            return Result.fail(HttpCode.SYSTEM_FAIL);
        }
        LambdaUpdateWrapper<SysMenu> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysMenu::getId, sysMenu.getId());
        update(sysMenu,lambdaUpdateWrapper);
        return Result.ok();
    }

    @Override
    public Result deleteMenu(int menuId) {
        LambdaUpdateWrapper<SysMenu> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysMenu::getId, menuId);
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysMenu::getParentId, menuId);
        if(list(lambdaQueryWrapper).size() > 0){
            return Result.fail(HttpCode.SYSTEM_FAIL);
        }
        remove(lambdaUpdateWrapper);
        return Result.ok();
    }
    @Override
    public Result treeSelect() {

        List<MenuTreeVo> newMenuTreeVos = getMenuTreeVos();
        return Result.ok(newMenuTreeVos);
    }
    @Override
    public   List<MenuTreeVo> getMenuTreeVos() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        List<SysMenu> Menulist = list(wrapper);
        List<MenuTreeVo> changeRoleVos =  new ArrayList<>();
        for (SysMenu sysMenu : Menulist) {
            MenuTreeVo menuTreeVo = MyCopyBeanUtil.copyBean(sysMenu, MenuTreeVo.class);
            menuTreeVo.setChildren(new ArrayList<>());
            menuTreeVo.setLabel(sysMenu.getMenuName());
            changeRoleVos.add(menuTreeVo);

        }
        List<MenuTreeVo> newMenuTreeVos = new ArrayList<>();
        for (MenuTreeVo menuTreeVo : changeRoleVos) {
            if(menuTreeVo.getParentId() != 0){
                for (MenuTreeVo changeRoleVo : changeRoleVos) {
                    if(Objects.equals(changeRoleVo.getId(), menuTreeVo.getParentId())){
                        changeRoleVo.getChildren().add(menuTreeVo);
                    }
                }
            }
            else {
                newMenuTreeVos.add(menuTreeVo);
            }
        }
        return newMenuTreeVos;
    }
}




