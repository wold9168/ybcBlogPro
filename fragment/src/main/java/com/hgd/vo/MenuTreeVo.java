package com.hgd.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVo {
    private List<MenuTreeVo> children;
    private Long id;
    private String label;
    private Long parentId;
}
