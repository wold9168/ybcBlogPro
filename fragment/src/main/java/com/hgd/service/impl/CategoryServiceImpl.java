package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.mapper.ArticleMapper;
import com.hgd.pojo.Article;
import com.hgd.pojo.Category;
import com.hgd.service.ArticleService;
import com.hgd.service.CategoryService;
import com.hgd.mapper.CategoryMapper;
import com.hgd.util.MyCopyBeanUtil;
import com.hgd.util.Result;
import com.hgd.vo.CategoryVo;
import com.hgd.vo.CategoryVo2;
import com.hgd.vo.ListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
* @author lenovo
* @description 针对表【category(分类表)】的数据库操作Service实现
* @createDate 2024-07-30 10:48:53
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Result getCategoryList() {
        Set<Long> catagoryIdSet =articleMapper.getCategoryIdSet();
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Category::getId,catagoryIdSet);
        lambdaQueryWrapper.select(Category::getId,Category::getName);
        List<Category> categoryList = this.list(lambdaQueryWrapper);
        return Result.ok(categoryList);
    }

    @Override
    public Result listAllCategory() {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Category::getId,Category::getName,Category::getDescription);
        return Result.ok(list(lambdaQueryWrapper));
    }

    @Override
    public Result listCategory(int pageNum, int pageSize, String name, String status) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Category::getId,Category::getName,Category::getDescription,Category::getStatus);
        if(StringUtils.hasText(name)){
            lambdaQueryWrapper.like(Category::getName,name);
        }
        if(StringUtils.hasText(status)){
            lambdaQueryWrapper.like(Category::getStatus,status);
        }
        Page<Category> categoryPage = new Page<>(pageNum, pageSize);
        Page<Category> page=page(categoryPage,lambdaQueryWrapper);
        List<Category> categorieList = page.getRecords();
        List<CategoryVo> categoryVoList=MyCopyBeanUtil.copyList(categorieList,CategoryVo.class);
        ListVo<CategoryVo> categoryListVo = new ListVo<>(page.getTotal(),categoryVoList);
        return Result.ok(categoryListVo);
    }

    @Override
    public Result addCategory(CategoryVo2 categoryVo2) {
        Category category = MyCopyBeanUtil.copyBean(categoryVo2,Category.class);
        save(category);
        return Result.ok();
    }

    @Override
    public Result getCategoryById(int id) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getId,id);
        CategoryVo categoryVo=MyCopyBeanUtil.copyBean(list(lambdaQueryWrapper).get(0),CategoryVo.class);
        return Result.ok(categoryVo);
    }

    @Override
    public Result updateCategory(CategoryVo categoryVo) {
        LambdaUpdateWrapper<Category> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Category::getId,categoryVo.getId());
        lambdaUpdateWrapper.set(Category::getName, categoryVo.getName());
        lambdaUpdateWrapper.set(Category::getDescription, categoryVo.getDescription());
        lambdaUpdateWrapper.set(Category::getStatus,categoryVo.getStatus());
        update(lambdaUpdateWrapper);
        return Result.ok();
    }

    @Override
    public Result deleteCategory(int id) {
        LambdaUpdateWrapper<Category> lambdaUpdateWrapper =new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Category::getId,id);
        lambdaUpdateWrapper.set(Category::getDelFlag,1);
        update(lambdaUpdateWrapper);
        return Result.ok();
    }
}




