package com.hgd.vo;

import lombok.Data;

import java.util.List;

@Data
public class AddUserVo {
    private String userName;
    private String nickName;
    private String password;
    private String phonenumber;
    private String email;
    private String sex;
    private String status;
    private List<Long> roleIds;
/*
"email":"weq@2132.com",
    "id":"14787164048663",
    "nickName":"sg777",
    "sex":"1",
    "status":"0",
    "userName":"sg777",
    "roleIds":[
        "11"
 */
}
