package com.hgd.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo3 {
    private Long categoryId;
    private String content;
    private Long createBy;
    private Date createTime;
    private Integer delFlag;
    private Long id;
    private String isComment;
    private String isTop;
    private String status;
    private String summary;
    List<Long> tags;
    private String thumbnail;
    private String title;
    private Long updateBy;
    private Date updateTime;
    private Long viewCount;

}
