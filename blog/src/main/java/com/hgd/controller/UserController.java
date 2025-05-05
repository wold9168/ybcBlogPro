package com.hgd.controller;

import com.hgd.pojo.SysUser;
import com.hgd.security.SecurityServiceImpl;
import com.hgd.service.SysUserService;
import com.hgd.util.Result;
import com.hgd.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private SecurityServiceImpl securityService;
    @Autowired
    private SysUserService sysUserService;
    @GetMapping("/user/userInfo")
    public Result userInfo() {
        return securityService.getUserInfo();
    }
    @PutMapping("/user/userInfo")
    public Result userInfo(@RequestBody UserVo userVo) {
        return sysUserService.userInfo(userVo);
    }
    @PostMapping("/user/register")
    public Result register(@RequestBody SysUser sysUser) {
        return sysUserService.register(sysUser);
    }
}
