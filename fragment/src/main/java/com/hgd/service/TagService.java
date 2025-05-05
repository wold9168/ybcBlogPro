package com.hgd.service;

import com.hgd.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hgd.util.Result;

/**
* @author lenovo
* @description 针对表【tag(标签)】的数据库操作Service
* @createDate 2024-08-02 15:52:01
*/
public interface TagService extends IService<Tag> {

    Result list(int pageNum, int pageSize, String name, String remark);
    Result tagById(int id);
    Result listAllTag();
}
