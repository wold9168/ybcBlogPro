package com.hgd.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo {
    private Long categoryId;
    private String categoryName;
    private Date createTime;
    private Long id;
    private String summary;
    private String thumbnail;
    private String title;
    private Long viewCount;
}
