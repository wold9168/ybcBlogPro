package com.hgd.service;

import com.hgd.pojo.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hgd.util.Result;
import com.hgd.vo.ChangeRoleVo;
import com.hgd.vo.RoleAddVo;
import com.hgd.vo.RoleUpdateVo;

import java.util.List;

/**
* @author lenovo
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2024-08-02 12:17:11
*/
public interface SysRoleService extends IService<SysRole> {

    Result getRoleList(int pageNum, int pageSize, String roleName, String status);

    Result changeRoleStatus(ChangeRoleVo changeRoleVo);

    Result addRole(RoleAddVo roleAddVo);

    Result getrole(int id);

    Result getRoleMenuTree(int id);

    Result updateRole(RoleUpdateVo roleUpdateVo);

    Result deleteRole(int id);

    List<SysRole> getroleById(List<Long> ids);

    Result treeselect();
}
