package com.hgd.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hgd.http.AppInfo;
import com.hgd.http.HttpCode;
import com.hgd.mapper.SysMenuMapper;
import com.hgd.mapper.SysRoleMapper;
import com.hgd.pojo.SysMenu;
import com.hgd.pojo.SysUser;
import com.hgd.service.SysMenuService;
import com.hgd.util.*;
import com.hgd.vo.GetInfoVo;
import com.hgd.vo.MenuVo;
import com.hgd.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SecurityServiceImpl{
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    public Result login(SysUser user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(authenticate != null){
            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            SysUser sysUser = userDetails.getUser();
            String token = JwtUtil.getToken(String.valueOf(sysUser.getId()));
            redisUtil.hSetAndTime(
                    AppInfo.ADMIN_REDIS_KEY,
                    String.valueOf(sysUser.getId()),
                    userDetails,1800);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            return Result.ok(map);
        }
        return Result.fail(HttpCode.USERNAME_PASSWORD_FAIL);
    }
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    public Result getInfo() {
        UserDetailsImpl userDetails = SecurityUtil.getAuthUser();
        if (userDetails.getUser().getId() == 1) {
            List<String> permsList = sysMenuMapper.getPermsAll();
            GetInfoVo getInfoVo = new GetInfoVo();
            getInfoVo.setPermissions(permsList);
            getInfoVo.setRoles(Arrays.asList("admin"));
            getInfoVo.setUser(MyCopyBeanUtil.copyBean(userDetails.getUser(),UserVo.class));
            return Result.ok(getInfoVo);
        }
        List<String> permsList = sysMenuMapper.getPermsByUserId(userDetails.getUser().getId());
        GetInfoVo getInfoVo = new GetInfoVo();
        getInfoVo.setPermissions(permsList);
        List<String> roleKeysByUserId = sysRoleMapper.getRoleKeysByUserId(userDetails.getUser().getId());
        getInfoVo.setRoles(roleKeysByUserId);
        getInfoVo.setUser(MyCopyBeanUtil.copyBean(userDetails.getUser(),UserVo.class));
        return Result.ok(getInfoVo);
    }

    public Result getRouters() {
        UserDetailsImpl userDetails = SecurityUtil.getAuthUser();
        if (userDetails.getUser().getId() == 1) {
            List<MenuVo> menuVoList = sysMenuMapper.getMenusAll();
            List<MenuVo> parentList = menuVoList.stream().filter(menuVo -> menuVo.getParentId() == 0).collect(Collectors.toList());
            for (MenuVo menuVo : parentList) {
                menuVo.setChildren(getResultMenuVoList(menuVoList, menuVo.getId()));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("menus", parentList);
            return Result.ok(map);
        }
        List<MenuVo> menuVoList = sysMenuMapper.getMenusByUserId(userDetails.getUser().getId());
        List<MenuVo> parentList = menuVoList.stream().filter(menuVo -> menuVo.getParentId() == 0).collect(Collectors.toList());
        for (MenuVo menuVo : parentList) {
            menuVo.setChildren(getResultMenuVoList(menuVoList, menuVo.getId()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("menus", parentList);
        return Result.ok(map);
    }
    public List<MenuVo> getResultMenuVoList(List<MenuVo> menuVoList, long parentId) {
        return menuVoList.stream().filter(menuVo -> menuVo.getParentId() == parentId).collect(Collectors.toList());
    }

    public Result logout() {
        UserDetailsImpl userDetails = SecurityUtil.getAuthUser();
        redisUtil.hRemove(AppInfo.ADMIN_REDIS_KEY, String.valueOf(userDetails.getUser().getId()));
        return Result.ok();
    }
}
