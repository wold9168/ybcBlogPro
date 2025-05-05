package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.pojo.SysUserRole;
import com.hgd.service.SysUserRoleService;
import com.hgd.mapper.SysUserRoleMapper;
import com.hgd.util.MyCopyBeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2024-08-02 12:17:11
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{
    @Override
    public List<Long> getRoleId(int id) {
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,id);
        wrapper.select(SysUserRole::getRoleId);
        List<SysUserRole> list = this.list(wrapper);
        return MyCopyBeanUtil.copyList(list,Long.class);
    }
}




