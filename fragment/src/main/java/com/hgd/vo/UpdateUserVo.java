package com.hgd.vo;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserVo {
    private String userName;
    private String nickName;
    private String phonenumber;
    private String email;
    private String sex;
    private String status;
    private List<Long> roleIds;
    private Long id;
}
