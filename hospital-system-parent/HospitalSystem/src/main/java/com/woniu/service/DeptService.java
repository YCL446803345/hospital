package com.woniu.service;

import com.woniu.entity.Dept;

import java.util.List;

/**
 * 部门业务层
 */
public interface DeptService {

    //查询部门列表
    List<Dept> findDeptList();
}
