package com.hgd.vo;

import lombok.Data;

import java.util.List;

@Data
public class GetInfoVo {
    private List<String> permissions;
    private List<String> roles;
    private UserVo user;
}
