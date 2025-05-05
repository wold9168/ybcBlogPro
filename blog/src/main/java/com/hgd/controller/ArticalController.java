package com.hgd.controller;

import com.hgd.service.ArticleService;
import com.hgd.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticalController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/article/hotArticleList")
    public Result hotArticleList() {
        return articleService.hotArticleList();
    }
    @GetMapping("/article/articleList")
    public Result articleList(int categoryId, int pageNum, int pageSize) {
        return articleService.articleList(categoryId, pageNum, pageSize);
    }
    @GetMapping("/article/{id}")
    public Result articleById(@PathVariable int id){
        return articleService.articleById(id);
    }
    @PutMapping("/article/updateViewCount/{id}")
    public Result updateViewCount(@PathVariable("id") int id) {
        return articleService.updateViewCount(id);
    }
}
