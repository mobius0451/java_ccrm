package com.ccrm.domain;

import com.ccrm.domain.entity.SysCollege;
import com.ccrm.domain.entity.SysMenu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2022-11-13 21:57
 * @Description: Treeselect树结构实体类
 */
@Data
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    public TreeSelect(SysCollege college) {
        this.id = college.getCollegeId();
        this.label = college.getCollegeName();
        this.children = college.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
