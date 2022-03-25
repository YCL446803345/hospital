package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;
import com.woniu.entity.Patient;
import com.woniu.mapper.InHospitalTableMapper;
import com.woniu.service.InHospitalTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InHospitalTableServiceImpl implements InHospitalTableService {

    @Autowired
    private InHospitalTableMapper inHospitalTableMapper;


    //分页查询预约住院申请列表
    public PageInfo<InHospitalTable> queryInHospitalTable(InHospitalTable inHospitalTable, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InHospitalTable> inHospitalTableList = inHospitalTableMapper.queryInHospitalTableList(inHospitalTable);
        PageInfo<InHospitalTable> inHospitalTablePageInfo = new PageInfo<>(inHospitalTableList);
        return inHospitalTablePageInfo;
    }

    //审核预约住院信息
    public void updateInHospitalTable(InHospitalTable inHospitalTable) {
        inHospitalTableMapper.updateInHospitalTable(inHospitalTable);
    }
}
