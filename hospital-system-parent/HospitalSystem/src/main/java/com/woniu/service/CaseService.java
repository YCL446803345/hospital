package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Case;

public interface CaseService {
    //分页模糊查询病例中心列表
    PageInfo<Case> queryCaseList(Case c, Integer pageNum, Integer pageSize);
}
