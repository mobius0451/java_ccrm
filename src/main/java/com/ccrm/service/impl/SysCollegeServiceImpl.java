package com.ccrm.service.impl;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.TreeSelect;
import com.ccrm.domain.entity.SysCollege;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.exception.CustomException;
import com.ccrm.mapper.SysCollegeMapper;
import com.ccrm.mapper.SysUserMapper;
import com.ccrm.service.ISysCollegeService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2022-11-13 21:43
 * @Description:
 */
@Service
public class SysCollegeServiceImpl extends ServiceImpl<SysCollegeMapper, SysCollege> implements ISysCollegeService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<TreeSelect> selectCollegeTreeList() {
        LambdaQueryWrapper<SysCollege> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCollege::getDelFlag, 0);
        List<SysCollege> colleges = this.baseMapper.selectList(queryWrapper);
        return buildDeptTreeSelect(colleges);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param colleges 学院班级列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysCollege> colleges)
    {
        List<SysCollege> deptTrees = buildDeptTree(colleges);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param colleges 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysCollege> buildDeptTree(List<SysCollege> colleges)
    {
        List<SysCollege> returnList = new ArrayList<SysCollege>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysCollege college : colleges)
        {
            tempList.add(college.getCollegeId());
        }
        for (SysCollege dept : colleges)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(colleges, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = colleges;
        }
        return returnList;
    }

    @Override
    public int saveCollege(SysCollege college) {
        SysCollege parent = this.baseMapper.selectById(college.getParentId());
        if (!UserConstants.COLLEGE_NORMAL.equals(parent.getStatus())) {
            throw new CustomException("部门停用，不允许新增");
        }
        college.setAncestors(parent.getAncestors() + "," + college.getParentId());
        int i = this.baseMapper.insert(college);
        return i;
    }

    @Override
    public int updateCollege(SysCollege college) {
        SysCollege parent = this.baseMapper.selectById(college.getParentId());
        SysCollege old = this.baseMapper.selectById(college.getCollegeId());
        if (StringUtils.isNotNull(parent) && StringUtils.isNotNull(old)) {
            String newAncestors = parent.getAncestors() + "," + parent.getCollegeId();
            String oldAncestors = old.getAncestors();
            college.setAncestors(newAncestors);
            updateCollegeChildren(college.getCollegeId(), newAncestors, oldAncestors);
        }
        int result = this.baseMapper.updateById(college);
        if (UserConstants.COLLEGE_NORMAL.equals(college.getStatus()) && StringUtils.isNotEmpty(college.getAncestors())
                && !StringUtils.equals("0", college.getAncestors()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            String ancestors = college.getAncestors();
            List<Long> ids = Arrays.stream(ancestors.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            for (Long id : ids) {
                this.baseMapper.updateStatusById(id, "0");
            }
        }
        return result;
    }

    @Override
    public String checkCollegeNameUnique(SysCollege college) {
        Long collegeId = StringUtils.isNull(college.getCollegeId()) ? -1L : college.getCollegeId();
        LambdaQueryWrapper<SysCollege> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCollege::getCollegeName, college.getCollegeName());
        queryWrapper.eq(SysCollege::getParentId, college.getParentId());
        SysCollege info = this.baseMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(info) && info.getCollegeId().longValue() != collegeId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int selectNormalChildrenCollegeById(Long collegeId) {
        LambdaQueryWrapper<SysCollege> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(collegeId != null, SysCollege::getAncestors, collegeId);
        queryWrapper.eq(SysCollege::getStatus, UserConstants.COLLEGE_NORMAL);
        int i = this.baseMapper.selectCount(queryWrapper);
        return i;
    }

    @Override
    public boolean hasChildByCollegeId(Long collegeId) {
        LambdaQueryWrapper<SysCollege> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCollege::getParentId, collegeId);
        queryWrapper.last("limit 1");
        int i = this.baseMapper.selectCount(queryWrapper);
        return i > 0;
    }

    @Override
    public boolean checkCollegeExistUser(Long collegeId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getCollegeId, collegeId);
        int i = userMapper.selectCount(queryWrapper);
        return i > 0;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysCollege> list, SysCollege college) {
        // 得到子节点列表
        List<SysCollege> childList = getChildList(list, college);
        college.setChildren(childList);
        for (SysCollege tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysCollege> getChildList(List<SysCollege> list, SysCollege college) {
        List<SysCollege> tlist = new ArrayList<SysCollege>();
        Iterator<SysCollege> it = list.iterator();
        while (it.hasNext()) {
            SysCollege n = (SysCollege) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == college.getCollegeId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysCollege> list, SysCollege college) {
        return getChildList(list, college).size() > 0;
    }

    /**
     * 修改子元素关系
     *
     * @param collegeId 被修改的班级ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateCollegeChildren(Long collegeId, String newAncestors, String oldAncestors) {
        LambdaQueryWrapper<SysCollege> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCollege::getParentId, collegeId);
        List<SysCollege> children = this.baseMapper.selectList(queryWrapper);
        if (children.size() > 0) {
            for (SysCollege child : children) {
                child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
                this.baseMapper.updateById(child);
            }
        }
    }
}
