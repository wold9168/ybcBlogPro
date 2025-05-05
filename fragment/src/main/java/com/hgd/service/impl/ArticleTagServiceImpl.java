package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.pojo.ArticleTag;
import com.hgd.service.ArticleTagService;
import com.hgd.mapper.ArticleTagMapper;
import com.hgd.util.MyCopyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author lenovo
* @description 针对表【article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2024-08-02 19:49:09
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{
    @Override
    public List<Long> getTagsByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper =  new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTagList =list(lambdaQueryWrapper);
        List<Long> tags = new ArrayList<>();
        for(ArticleTag articleTag:articleTagList){
            tags.add(articleTag.getTagId());
        }
        return tags;
    }
}




