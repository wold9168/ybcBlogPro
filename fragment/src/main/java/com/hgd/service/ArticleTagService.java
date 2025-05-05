package com.hgd.service;

import com.hgd.pojo.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lenovo
* @description 针对表【article_tag(文章标签关联表)】的数据库操作Service
* @createDate 2024-08-02 19:49:09
*/
public interface ArticleTagService extends IService<ArticleTag> {

    List<Long> getTagsByArticleId(Long id);
}
