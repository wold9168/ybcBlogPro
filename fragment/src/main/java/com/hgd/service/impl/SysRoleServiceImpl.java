package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.pojo.SysRole;
import com.hgd.pojo.SysRoleMenu;
import com.hgd.service.SysMenuService;
import com.hgd.service.SysRoleMenuService;
import com.hgd.service.SysRoleService;
import com.hgd.mapper.SysRoleMapper;
import com.hgd.util.MyCopyBeanUtil;
import com.hgd.util.Result;
import com.hgd.vo.ChangeRoleVo;
import com.hgd.vo.MenuTreeVo;
import com.hgd.vo.RoleAddVo;
import com.hgd.vo.RoleUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2024-08-02 12:17:11
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{
    @Override
    public Result getRoleList(int pageNum, int pageSize, String roleName, String status) {
        Page<SysRole> Rolepage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysRole::getRoleName,SysRole::getId,SysRole::getRoleKey,SysRole::getStatus,SysRole::getRoleSort);

        if(StringUtils.hasText(roleName)){
            wrapper.like(SysRole::getRoleName, roleName);
        }
        if(StringUtils.hasText(status)){
            wrapper.eq(SysRole::getStatus, status);
        }
        wrapper.orderByAsc(SysRole::getRoleSort);
        Page<SysRole> page = page(Rolepage, wrapper);
        List<SysRole> records = page.getRecords();
        HashMap<String ,Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("rows", records);
        return Result.ok(map);
    }

    @Override
    public Result changeRoleStatus(ChangeRoleVo changeRoleVo) {

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getId,changeRoleVo.getRoleId());
        SysRole one = getOne(wrapper);
        one.setStatus(changeRoleVo.getStatus());
        updateById(one);
        return Result.ok();
    }
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public Result addRole(RoleAddVo roleAddVo) {

        SysRole sysRole = MyCopyBeanUtil.copyBean(roleAddVo, SysRole.class);
        save(sysRole);
        for (Long menuId : roleAddVo.getMenuIds()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuService.save(sysRoleMenu);
        }
        return Result.ok();
    }

    @Override
    public Result getrole(int id) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getId,id);
        SysRole one = getOne(wrapper);
        wrapper.select(SysRole::getRoleSort,
                SysRole::getRoleKey,SysRole::getStatus,
                SysRole::getRoleName,SysRole::getId,SysRole::getRemark);
        return Result.ok(one);
    }
    @Autowired
    private SysMenuService service;

    @Override
    public Result getRoleMenuTree(int id) {

        List<MenuTreeVo> menuTreeVos = service.getMenuTreeVos();
        HashMap<String ,Object> map = new HashMap<>();
        map.put("menus",menuTreeVos);
        List<Long> longs = sysRoleMenuService.getcheckedKeys(Long.valueOf(id));
        map.put("checkedKeys",longs);
        return Result.ok(map);
    }

    @Override
    public Result updateRole(RoleUpdateVo roleUpdateVo) {
        SysRole sysRole = MyCopyBeanUtil.copyBean(roleUpdateVo, SysRole.class);
        Long id = roleUpdateVo.getId();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getId,id);
        SysRole one = getOne(wrapper);
        updateById(sysRole);
        return Result.ok();
    }

    @Override
    public Result deleteRole(int id) {
        removeById(id);
        return Result.ok();
    }

    @Override
    public List<SysRole> getroleById(List<Long> ids) {
        List<SysRole> roleList = new ArrayList<>();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        for (Long id : ids) {
            wrapper.eq(SysRole::getId,id);
            SysRole one = getOne(wrapper);
            roleList.add(one);
        }
        return roleList;
    }

    @Override
    public Result treeselect() {
        List<MenuTreeVo> menuTreeVos = service.getMenuTreeVos();
        return Result.ok(menuTreeVos);
    }
}




