package com.hgd.controller;

import com.hgd.service.SysUserService;
import com.hgd.util.Result;
import com.hgd.vo.AddUserVo;
import com.hgd.vo.UpdateUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/system/user/list")
    @PreAuthorize("@ps.prePost('system/user/index')")
    public Result list(int pageNum, int pageSize,String userName,String phonenumber,String status){
        return  sysUserService.list(pageNum,pageSize,userName,phonenumber,status);
    }

    @GetMapping("/system/role/listAllRole")
    @PreAuthorize("@ps.prePost('system/user/index')")
    public Result listAllRole(){
        return sysUserService.listAllRole();
    }

    @PostMapping("/system/user")
    @PreAuthorize("@ps.prePost('system/user/index')")
    public Result addUser(@RequestBody AddUserVo addUserVo) {
       return sysUserService.addUser(addUserVo);
    }

    @DeleteMapping("/system/user/{id}")
    @PreAuthorize("@ps.prePost('system/user/index')")
    public Result deleteUser(@PathVariable("id") int id) {
        return sysUserService.deleteUser(id);
    }

    @GetMapping("/system/user/{id}")
    @PreAuthorize("@ps.prePost('system/user/index')")
    public Result getUser(@PathVariable("id") int id) {
        return sysUserService.getUser(id);
    }

    @PutMapping("/system/user")
    @PreAuthorize("@ps.prePost('system/user/index')")
    public Result updateUser(@RequestBody UpdateUserVo updateUserVo) {
        return sysUserService.updateUser(updateUserVo);
    }
}
