package com.hgd.controller;

import com.hgd.pojo.SysMenu;
import com.hgd.service.SysMenuService;
import com.hgd.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @GetMapping("/system/menu/list")
    @PreAuthorize("@ps.prePost('system/menu/index')")
    public Result menuList(String status,String menuName){
        return sysMenuService.menuList(status,menuName);
    }
    @PostMapping("/system/menu")
    @PreAuthorize("@ps.prePost('system/menu/index')")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.addMenu(sysMenu);
    }
    @GetMapping("/system/menu/{id}")
    @PreAuthorize("@ps.prePost('system/menu/index')")
    public Result getMenuById(@PathVariable("id") int id) {
        return sysMenuService.getMenuById(id);
    }
    @PutMapping("/system/menu")
    @PreAuthorize("@ps.prePost('system/menu/index')")
    public Result putMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.putMenu(sysMenu);
    }
    @DeleteMapping("/system/menu/{id}")
    @PreAuthorize("@ps.prePost('system/menu/index')")
    public Result deleteMenu(@PathVariable("id") int menuId){
        return sysMenuService.deleteMenu(menuId);
    }
}
