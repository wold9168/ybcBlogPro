package com.hgd.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDto {
    private String title;
    private String thumbnail;
    private String isTop;
    private String isComment;
    private String content;
    private List<Long> tags;
    private Long categoryId;
    private String summary;
    private String status;
}
