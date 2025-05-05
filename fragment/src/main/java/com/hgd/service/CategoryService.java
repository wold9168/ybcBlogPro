package com.hgd.service;

import com.hgd.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hgd.util.Result;
import com.hgd.vo.CategoryVo;
import com.hgd.vo.CategoryVo2;

/**
* @author lenovo
* @description 针对表【category(分类表)】的数据库操作Service
* @createDate 2024-07-30 10:48:53
*/
public interface CategoryService extends IService<Category> {
    public Result getCategoryList();

    Result listAllCategory();

    Result listCategory(int pageNum, int pageSize, String name, String status);

    Result addCategory(CategoryVo2 categoryVo2);

    Result getCategoryById(int id);

    Result updateCategory(CategoryVo categoryVo);

    Result deleteCategory(int id);
}
