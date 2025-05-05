package com.hgd.controller;


import com.hgd.service.SysRoleService;
import com.hgd.util.Result;
import com.hgd.vo.ChangeRoleVo;
import com.hgd.vo.RoleAddVo;
import com.hgd.vo.RoleUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/system/role/list")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result getRoleList(int pageNum, int pageSize, String roleName, String status) {
        return sysRoleService.getRoleList(pageNum,pageSize,roleName,status);
    }
    @PutMapping("/system/role/changeStatus")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result changeRoleStatus(@RequestBody ChangeRoleVo changeRoleVo) {
        return sysRoleService.changeRoleStatus(changeRoleVo);
    }
    @GetMapping("/system/menu/treeselect")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result treeselect() {
        return sysRoleService.treeselect();
    }
    @PostMapping("/system/role")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result addRole(@RequestBody RoleAddVo roleAddVo) {
        return sysRoleService.addRole(roleAddVo);
    }
    @GetMapping("/system/role/{id}")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result getrole(@PathVariable("id") int id) {
        return sysRoleService.getrole(id);
    }
    @GetMapping("/system/menu/roleMenuTreeselect/{id}")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result getRoleMenuTree(@PathVariable("id") int id) {
        return sysRoleService.getRoleMenuTree(id);
    }
    @PutMapping("/system/role")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result updateRole(@RequestBody RoleUpdateVo roleUpdateVo) {
        return sysRoleService.updateRole(roleUpdateVo);
    }
    @DeleteMapping("/system/role/{id}")
    @PreAuthorize("@ps.prePost('system/role/index')")
    public Result deleteRole(@PathVariable("id") int id) {
        return sysRoleService.deleteRole(id);
    }
}
