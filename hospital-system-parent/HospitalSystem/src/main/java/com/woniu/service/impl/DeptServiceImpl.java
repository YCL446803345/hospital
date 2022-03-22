package com.woniu.service.impl;

import com.woniu.entity.Dept;
import com.woniu.mapper.DeptMapper;
import com.woniu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 获取科室列表
     * @return
     */
    @Override
    public List<Dept> findDepts() {
        return deptMapper.selectByExample(null);
    }
}
