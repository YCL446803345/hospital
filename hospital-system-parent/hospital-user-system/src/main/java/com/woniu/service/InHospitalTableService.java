package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;

import java.util.List;

public interface InHospitalTableService {

    //分页查询预约住院申请列表
    PageInfo<InHospitalTable> queryInHospitalTable(InHospitalTable inHospitalTable, Integer pageNum, Integer pageSize);

    //审核入院信息
    void updateInHospitalTable(InHospitalTable inHospitalTable);
    //添加病人预约
    void addInHospitalTableService(InHospitalTable inHospitalTable);
    //根据手机号码查询病人预约信息
    List<InHospitalTable> getInHospitalTableByTelephone(String telephone);
}
