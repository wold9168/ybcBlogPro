package com.hgd.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hgd.http.AppInfo;
import com.hgd.http.HttpCode;
import com.hgd.pojo.SysUser;
import com.hgd.service.SysUserService;
import com.hgd.util.*;
import com.hgd.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityServiceImpl {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;

    public Result login(SysUser user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authenticate != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            SysUser sysUser = userDetails.getSysUser();
            String token = JwtUtil.getToken(String.valueOf(sysUser.getId()));
            UserVo userVo = MyCopyBeanUtil.copyBean(sysUser, UserVo.class);
            redisUtil.hSetAndTime(
                    AppInfo.USER_REDIS_KEY,
                    String.valueOf(sysUser.getId()),
                    userDetails,1800);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userInfo", userVo);
            return Result.ok(map);
        }
        return Result.fail(HttpCode.USERNAME_PASSWORD_FAIL);
    }

    public Result logout() {
        try {
            UserDetailsImpl userDetails =
                    (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long id = userDetails.getSysUser().getId();
            redisUtil.hRemove(AppInfo.USER_REDIS_KEY, String.valueOf(id));
        } catch (Exception e) {
        }
        return Result.ok();
    }
    @Autowired
    private SysUserService sysUserService;
    public Result getUserInfo() {
        UserDetailsImpl userDetails = SecurityUtil.getAuthUser();
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysUser::getId, userDetails.getSysUser().getId());
        lambdaQueryWrapper.select(SysUser::getAvatar,SysUser::getEmail,SysUser::getId,SysUser::getNickName,SysUser::getSex);
        SysUser sysUser = sysUserService.getOne(lambdaQueryWrapper);
        UserVo userVo = MyCopyBeanUtil.copyBean(sysUser, UserVo.class);
        return Result.ok(userVo);
    }
}
