package com.hgd.mapper;

import com.hgd.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgd.vo.AllRoleVo;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2024-08-02 12:17:11
* @Entity com.hgd.pojo.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> getRoleKeysByUserId(Long id);

    List<AllRoleVo> getAllRole();
}




