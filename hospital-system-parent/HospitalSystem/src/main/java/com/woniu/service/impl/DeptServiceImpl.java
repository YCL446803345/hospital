package com.woniu.service.impl;

import com.woniu.entity.Dept;
import com.woniu.entity.DeptExample;
import com.woniu.mapper.DeptMapper;
import com.woniu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门实现业务
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {


    @Autowired
    private DeptMapper deptMapper;


    //查询部门列表
    public List<Dept> findDeptList() {
        List<Dept> deptList = deptMapper.selectByExample(new DeptExample());

        return deptList;
    }

}
