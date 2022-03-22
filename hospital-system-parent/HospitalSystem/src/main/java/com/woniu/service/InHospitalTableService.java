package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;

public interface InHospitalTableService {

    //分页查询预约住院申请列表
    PageInfo<InHospitalTable> queryInHospitalTable(InHospitalTable inHospitalTable, Integer pageNum, Integer pageSize);

}
