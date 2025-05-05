package com.hgd.service;

import com.hgd.dto.ArticleDto;
import com.hgd.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hgd.util.Result;
import com.hgd.vo.ArticleVo3;

/**
* @author lenovo
* @description 针对表【article(文章表)】的数据库操作Service
* @createDate 2024-07-30 10:31:32
*/
public interface ArticleService extends IService<Article> {

    Result hotArticleList();

    Result articleList(int categoryId, int pageNum, int pageSize);

    Result articleById(int id);

    Result updateViewCount(int id);

    Result article(ArticleDto articleDto);

    Result list(int pageNum, int pageSize, String title, String summary);

    Result articleDetailById(int id);

    Result updateArticle(ArticleVo3 articleVo3);

    Result deleteArticle(int id);
}
