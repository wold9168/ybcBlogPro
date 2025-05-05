package com.hgd.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hgd.mapper.SysUserMapper;
import com.hgd.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUserName, s);
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        return new UserDetailsImpl(sysUser);
    }

}
