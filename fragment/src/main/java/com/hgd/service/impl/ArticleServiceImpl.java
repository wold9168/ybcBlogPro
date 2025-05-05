package com.hgd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgd.dto.ArticleDto;
import com.hgd.pojo.Article;
import com.hgd.pojo.ArticleTag;
import com.hgd.pojo.Category;
import com.hgd.pojo.Tag;
import com.hgd.service.ArticleService;
import com.hgd.mapper.ArticleMapper;
import com.hgd.service.ArticleTagService;
import com.hgd.service.CategoryService;
import com.hgd.util.MyCopyBeanUtil;
import com.hgd.util.RedisUtil;
import com.hgd.util.Result;
import com.hgd.vo.ArticleVo;
import com.hgd.vo.ArticleVo2;
import com.hgd.vo.ArticleVo3;
import com.hgd.vo.ListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hgd.http.AppInfo.VIEW_COUNT;

/**
* @author lenovo
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2024-07-30 10:31:32
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Result hotArticleList() {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.select(Article::getId,Article::getTitle,Article::getViewCount);
        articleLambdaQueryWrapper.orderByDesc(Article::getViewCount);
        articleLambdaQueryWrapper.last("limit 5");
        return Result.ok(list(articleLambdaQueryWrapper));
    }
    @Override
    public Result articleList(int categoryId, int pageNum, int pageSize) {
        if (categoryId == 0) {
            LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleLambdaQueryWrapper.select(Article::getCategoryId,
                    Article::getCreateTime, Article::getId, Article::getSummary,
                    Article::getThumbnail, Article::getTitle, Article::getViewCount);
            articleLambdaQueryWrapper.eq(Article::getStatus, 0);
            articleLambdaQueryWrapper.orderByDesc(Article::getIsTop);
            //设置分页信息
            Page<Article> articlePage = new Page<>(pageNum, pageSize);
            Page<Article> page = page(articlePage, articleLambdaQueryWrapper);
            //获取结果
            List<Article> articleList = page.getRecords();
            List<ArticleVo> list = MyCopyBeanUtil.copyList(articleList, ArticleVo.class);
            for (ArticleVo articleVo : list) {
                articleVo.setViewCount(Long.parseLong(redisUtil.hGet(VIEW_COUNT,String.valueOf(articleVo.getId())).toString()));
                LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
                categoryLambdaQueryWrapper.select(Category::getName);
                categoryLambdaQueryWrapper.eq(Category::getId, articleVo.getCategoryId());
                Category one = categoryService.getOne(categoryLambdaQueryWrapper);
                articleVo.setCategoryName(one.getName());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("total", page.getTotal());
            map.put("rows", list);
            return Result.ok(map);
        }
        List<ArticleVo> articleListByCategoryId =
                getBaseMapper().getArticleListByCategoryId(categoryId, pageNum - 1, pageSize);
        int total = getBaseMapper().getArticleListByCategoryIdToCount(categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", articleListByCategoryId);
        return Result.ok(map);
    }
    @Override
    public Result articleById(int id) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getId,id);
        articleLambdaQueryWrapper.select(Article::getCategoryId,Article::getContent,Article::getCreateTime,Article::getId,Article::getIsComment,Article::getIsTop,Article::getTitle);
        Article article = getOne(articleLambdaQueryWrapper);
        ArticleVo2 articleVo2 = MyCopyBeanUtil.copyBean(article, ArticleVo2.class);
        Category category = categoryService.getById(articleVo2.getCategoryId());
        articleVo2.setCategoryName(category.getName());
        articleVo2.setViewCount(Long.parseLong(redisUtil.hGet(VIEW_COUNT,String.valueOf(articleVo2.getId())).toString()));
        return Result.ok(articleVo2);
    }

    @Override
    public Result updateViewCount(int id) {
        redisUtil.hIncr(VIEW_COUNT,String.valueOf(id),1);
        return Result.ok();
    }
    @Autowired
    private ArticleTagService articleTagService;
    @Transactional //加到同一事务
    @Override
    public Result article(ArticleDto articleDto) {
        Article article = MyCopyBeanUtil.copyBean(articleDto, Article.class);
        save(article);
        List<Long> tags = articleDto.getTags();
        List<ArticleTag> articleTagList =
                tags.stream().map(aLong -> new ArticleTag(article.getId(),aLong)).collect(Collectors.toList());
        articleTagService.saveBatch(articleTagList);
        return Result.ok();
    }

    @Override
    public Result list(int pageNum, int pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.select(Article::getCategoryId,Article::getContent,
                Article::getCreateTime,Article::getId,Article::getIsComment,
                Article::getIsTop,Article::getStatus,Article::getSummary,
                Article::getThumbnail,Article::getTitle,Article::getViewCount);
        articleLambdaQueryWrapper.eq(Article::getDelFlag,0);
        if (StringUtils.hasText(title)) {
            articleLambdaQueryWrapper.like(Article::getTitle, title);
        }
        if (StringUtils.hasText(summary)) {
            articleLambdaQueryWrapper.like(Article::getSummary, summary);
        }
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        Page<Article> page = page(articlePage, articleLambdaQueryWrapper);
        List<Article> articleList = page.getRecords();
        ListVo<Article> articleListVo = new ListVo<>(page.getTotal(), articleList);
        return Result.ok(articleListVo);
    }
    @Override
    public Result articleDetailById(int id) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getId, id);
        List<Article> articleList=list(lambdaQueryWrapper);
        List<ArticleVo3> articleVo3List = MyCopyBeanUtil.copyList(articleList, ArticleVo3.class);
        for(ArticleVo3 articleVo3 : articleVo3List){
            List<Long> tags = articleTagService.getTagsByArticleId(articleVo3.getId());
            articleVo3.setTags(tags);
        }
        return Result.ok(articleVo3List.get(0));
    }

    @Override
    public Result updateArticle(ArticleVo3 articleVo3) {
        Article article =MyCopyBeanUtil.copyBean(articleVo3, Article.class);
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,article.getId());
        update(article,lambdaUpdateWrapper);
        List<Long> tags = articleVo3.getTags();
        for(Long tag : tags){
            //标签的删除没有实现
            articleTagService.save(new ArticleTag(article.getId(),tag));
        }
        return Result.ok();
    }

    @Override
    public Result deleteArticle(int id) {
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,id);
        lambdaUpdateWrapper.set(Article::getDelFlag,1);
        update(lambdaUpdateWrapper);
        return Result.ok();
    }
}




