package com.hgd.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AllRoleVo {
        private Long createBy;
        private Date  createTime;
        private String delFlag;
        private Long id;
        private String remark;
        private String roleKey;
        private String roleName;
        private String roleSort;
        private String status;
        private Long updateBy;

}
