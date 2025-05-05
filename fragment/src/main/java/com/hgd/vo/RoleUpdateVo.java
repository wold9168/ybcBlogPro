package com.hgd.vo;

import lombok.Data;

import java.util.List;
@Data
public class RoleUpdateVo {
    private Long id;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private List<Long> menuIds;
    private String remark;
}
