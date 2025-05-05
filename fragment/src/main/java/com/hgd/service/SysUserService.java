package com.hgd.service;

import com.hgd.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hgd.util.Result;
import com.hgd.vo.AddUserVo;
import com.hgd.vo.UpdateUserVo;
import com.hgd.vo.UserVo;

/**
* @author lenovo
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-07-31 12:31:17
*/
public interface SysUserService extends IService<SysUser> {
    Result userInfo(UserVo userVo);

    public Result register(SysUser user);

    Result list(int pageNum, int pageSize, String userName, String phonenumber, String status);

    Result listAllRole();

    Result addUser(AddUserVo addUserVo);

    Result deleteUser(int id);

    Result getUser(int id);

    Result updateUser(UpdateUserVo updateUserVo);
}
