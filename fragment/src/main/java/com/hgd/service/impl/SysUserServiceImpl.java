package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.http.HttpCode;
import com.hgd.mapper.SysRoleMapper;
import com.hgd.pojo.SysRole;
import com.hgd.pojo.SysUser;
import com.hgd.pojo.SysUserRole;
import com.hgd.service.SysRoleService;
import com.hgd.service.SysUserRoleService;
import com.hgd.service.SysUserService;
import com.hgd.mapper.SysUserMapper;
import com.hgd.util.MyCopyBeanUtil;
import com.hgd.util.Result;
import com.hgd.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-07-31 12:31:17
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
    @Override
    public Result userInfo(UserVo userVo) {
        LambdaUpdateWrapper<SysUser> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId,userVo.getId());
        updateWrapper.set(SysUser::getNickName,userVo.getNickName());
        updateWrapper.set(SysUser::getEmail,userVo.getEmail());
        updateWrapper.set(SysUser::getSex,userVo.getSex());
        updateWrapper.set(SysUser::getAvatar,userVo.getAvatar());
        update(updateWrapper);
        return Result.ok();
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Result register(SysUser user) {
        String userName = user.getUserName();
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUserName, userName);
        SysUser sysUser = getOne(sysUserLambdaQueryWrapper);
        if (sysUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new RuntimeException("请填写昵称");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new RuntimeException("请填写邮箱");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return Result.ok();
    }

    @Override
    public Result list(int pageNum, int pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.hasText(userName)){
            queryWrapper.like(SysUser::getUserName,userName);
        }
        if(StringUtils.hasText(phonenumber)){
            queryWrapper.like(SysUser::getPhonenumber,phonenumber);
        }
        if(StringUtils.hasText(status)){
            queryWrapper.eq(SysUser::getStatus,status);
        }
        queryWrapper.eq(SysUser::getDelFlag,"0");
        queryWrapper.select(SysUser::getId,SysUser::getUserName,SysUser::getNickName,
                SysUser::getEmail,SysUser::getPhonenumber,SysUser::getStatus,
                SysUser::getCreateTime,SysUser::getUpdateTime,SysUser::getSex,
                SysUser::getAvatar, SysUser::getStatus);
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        Page<SysUser> userPage = page(page, queryWrapper);
        List<SysUser> userList=userPage.getRecords();
        ListVo<SysUser> listVo = new ListVo<>(userPage.getTotal(), userList);
        return Result.ok(listVo);
    }
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public Result listAllRole() {
        List<AllRoleVo> allRole = sysRoleMapper.getAllRole();
        return Result.ok(allRole);
    }
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Override
    public Result addUser(AddUserVo addUserVo) {
        SysUser user = MyCopyBeanUtil.copyBean(addUserVo, SysUser.class);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        for (Long roleId : addUserVo.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(user.getId());
            sysUserRoleService.save(sysUserRole);
        }
        return Result.ok();
    }

    @Override
    public Result deleteUser(int id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String name = principal.getName();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getId, id);
        SysUser one = getOne(wrapper);
        if(one.getUserName().equals(name)) {
            return Result.fail(HttpCode.SYSTEM_FAIL);
        } else {
            removeById(one);
            return Result.ok();
        }
    }
    @Autowired
    private SysRoleService sysRoleService;
    @Override
    public Result getUser(int id) {
        List<Long> list = sysUserRoleService.getRoleId(id);
        List<SysRole> roleList = sysRoleService.list();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysUser::getEmail,SysUser::getId,SysUser::getNickName,SysUser::getSex,
                SysUser::getStatus,SysUser::getUserName);
        wrapper.eq(SysUser::getId, id);
        SysUser one = getOne(wrapper);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("roleIds",list);
        map.put("roles",roleList);
        map.put("user",one);
        return Result.ok(map);
    }

    @Override
    public Result updateUser(UpdateUserVo updateUserVo) {
        SysUser user = MyCopyBeanUtil.copyBean(updateUserVo, SysUser.class);
        updateById(user);
        sysUserRoleService.removeById(user.getId());
        for (Long roleId : updateUserVo.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(user.getId());
            sysUserRoleService.save(sysUserRole);
        }
        return Result.ok();
    }
}




