package com.hgd.controller;

import com.alibaba.excel.EasyExcel;
import com.hgd.ex.CategoryEX;
import com.hgd.pojo.Category;
import com.hgd.service.CategoryService;
import com.hgd.util.MyCopyBeanUtil;
import com.hgd.util.Result;
import com.hgd.vo.CategoryVo;
import com.hgd.vo.CategoryVo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/content/category/listAllCategory")
    public Result listAllCategory() {
        return categoryService.listAllCategory();
    }
    @GetMapping("/content/category/export")
    @PreAuthorize("@ps.prePost('content:category:list')")
    public void export(HttpServletResponse response) throws IOException {
        List<Category> categoryList = categoryService.list();
        List<CategoryEX> categoryEXES = MyCopyBeanUtil.copyList(categoryList, CategoryEX.class);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fname = URLEncoder.encode("分类管理.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fname);
        EasyExcel.write(response.getOutputStream(), CategoryEX.class).sheet("模板").doWrite(categoryEXES);
    }
    @GetMapping("/content/category/list")
    @PreAuthorize("@ps.prePost('content:category:list')")
    public Result listCategory(int pageNum,int pageSize,String name,String status){
        return categoryService.listCategory(pageNum,pageSize,name,status);
    }
    @PostMapping("/content/category")
    @PreAuthorize("@ps.prePost('content:category:list')")
    public Result addCategory(@RequestBody CategoryVo2 categoryVo2){
        return categoryService.addCategory(categoryVo2);
    }
    @GetMapping("/content/category/{id}")
    @PreAuthorize("@ps.prePost('content:category:list')")
    public Result getCategoryById(@PathVariable("id") int id){
        return categoryService.getCategoryById(id);
    }
    @PutMapping("/content/category")
    @PreAuthorize("@ps.prePost('content:category:list')")
    public Result updateCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.updateCategory(categoryVo);
    }
    @DeleteMapping("/content/category/{id}")
    @PreAuthorize("@ps.prePost('content:category:list')")
    public Result deleteCategory(@PathVariable("id") int id){
        return categoryService.deleteCategory(id);
    }

}
