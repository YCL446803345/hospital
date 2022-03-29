package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Case;
import com.woniu.entity.DrugOut;
import com.woniu.mapper.CaseMapper;
import com.woniu.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    private CaseMapper caseMapper;

    //分页模糊查询病例中心列表
    public PageInfo<Case> queryCaseList(Case c, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Case> caseList = caseMapper.queryCaseList(c);
        PageInfo<Case> casePageInfo = new PageInfo<>(caseList);
        return casePageInfo;
    }
}
