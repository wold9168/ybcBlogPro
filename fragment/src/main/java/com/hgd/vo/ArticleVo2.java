package com.hgd.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo2 {
    private Long categoryId;
    private String categoryName;
    private String content;
    private Date createTime;
    private Long id;
    public String isComment;
    public String isTop;
    private String title;
    private Long viewCount;
}
