package com.ccrm.service;

import com.ccrm.domain.TreeSelect;
import com.ccrm.domain.entity.SysCollege;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @CreateTime: 2022-11-13 21:43
 * @Description:
 */
public interface ISysCollegeService extends IService<SysCollege> {

    /**
     * 查询班级树结构信息
     *
     * @return 部门树信息集合
     */
    List<TreeSelect> selectCollegeTreeList();

    /**
     * 构建前端所需要下拉树结构
     *
     * @param colleges 部门列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildDeptTreeSelect(List<SysCollege> colleges);

    /**
     * 构建前端所需要树结构
     *
     * @param colleges 部门列表
     * @return 树结构列表
     */
    List<SysCollege> buildDeptTree(List<SysCollege> colleges);

    /**
     * 新增学院班级
     * @param college
     * @return
     */
    int saveCollege(SysCollege college);

    /**
     * 修改学院班级
     * @param college
     * @return
     */
    int updateCollege(SysCollege college);

    /**
     * 检查学院名是否重复
     * @param college
     * @return
     */
    String checkCollegeNameUnique(SysCollege college);

    /**
     * 根据ID查询所有子班级（正常状态）
     * @param collegeId
     * @return 子班级数
     */
    int selectNormalChildrenCollegeById(Long collegeId);

    /**
     * 是否存在学院班级子节点
     * @param collegeId
     * @return
     */
    boolean hasChildByCollegeId(Long collegeId);

    /**
     * 查询班级是否存在用户
     * @param collegeId
     * @return
     */
    boolean checkCollegeExistUser(Long collegeId);
}
