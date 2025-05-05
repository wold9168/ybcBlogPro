package com.hgd.cron;

import com.hgd.http.AppInfo;
import com.hgd.pojo.Article;
import com.hgd.service.ArticleService;
import com.hgd.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountUpCron {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void viewCountUp() {
        Map<String, Integer> objectObjectMap = redisUtil.hGet(AppInfo.VIEW_COUNT);
        Set<Map.Entry<String, Integer>> entries = objectObjectMap.entrySet();
        List<Article> articleList = entries.stream().map(stringLongEntry -> {
            Article article = new Article();
            article.setId(Long.parseLong(stringLongEntry.getKey()));
            article.setViewCount(new Long(stringLongEntry.getValue()));
            return article;
        }).collect(Collectors.toList());
        articleService.updateBatchById(articleList);
    }
}
