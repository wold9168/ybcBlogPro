package com.hgd.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hgd.http.AppInfo;
import com.hgd.pojo.Article;
import com.hgd.service.ArticleService;
import com.hgd.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewCountToRedisRunner implements CommandLineRunner {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void run(String... args) throws Exception {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Article::getId, Article::getViewCount);
        List<Article> articleList = articleService.list(lambdaQueryWrapper);
        for(Article article : articleList){
            redisUtil.hSet(AppInfo.VIEW_COUNT, String.valueOf(article.getId()), article.getViewCount());
        }
    }
}
