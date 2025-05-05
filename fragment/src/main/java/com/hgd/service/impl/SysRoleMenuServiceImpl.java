package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.pojo.SysRoleMenu;
import com.hgd.service.SysRoleMenuService;
import com.hgd.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author lenovo
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2024-08-02 12:17:11
*/
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
    implements SysRoleMenuService{
    @Override
    public List<Long> getcheckedKeys(Long id) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysRoleMenu::getMenuId);
        queryWrapper.eq(SysRoleMenu::getRoleId, id);
        List<SysRoleMenu> list = list(queryWrapper);
        List<Long> collect = list.stream().map(sysRoleMenu -> sysRoleMenu.getMenuId()).collect(Collectors.toList());
        return collect;
    }
}




