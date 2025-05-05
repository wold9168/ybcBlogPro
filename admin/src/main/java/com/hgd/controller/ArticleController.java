package com.hgd.controller;

import com.hgd.dto.ArticleDto;
import com.hgd.service.ArticleService;
import com.hgd.util.Result;
import com.hgd.vo.ArticleVo3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/content/article")
    @PreAuthorize("@ps.prePost('content:article:writer')")
    public Result article(@RequestBody ArticleDto articleDto) {
        return articleService.article(articleDto);
    }
    @GetMapping("/content/article/list")
    @PreAuthorize("@ps.prePost('content:article:list')")
    public Result list(int pageNum, int pageSize, String title, String summary) {
        return articleService.list(pageNum, pageSize, title, summary);
    }
    @GetMapping("/content/article/{id}")
    @PreAuthorize("@ps.prePost('content:article:list')")
    public Result articleDetailById(@PathVariable("id") int id) {
        return articleService.articleDetailById(id);
    }
    @PutMapping("/content/article")
    @PreAuthorize("@ps.prePost('content:article:list')")
    public Result updateArticle(@RequestBody ArticleVo3 articleVo3) {
        return articleService.updateArticle(articleVo3);
    }
    @DeleteMapping("/content/article/{id}")
    @PreAuthorize("@ps.prePost('content:article:list')")
    public Result deleteArticle(@PathVariable("id") int id) {
        return articleService.deleteArticle(id);
    }
}
