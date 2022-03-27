package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;
import com.woniu.entity.InHospitalTableExample;
import com.woniu.mapper.InHospitalTableMapper;
import com.woniu.service.InHospitalTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    //添加预约住院信息
    public void addInHospitalTableService(InHospitalTable inHospitalTable) {
        inHospitalTable.setInHosptialTime(new Date());
        inHospitalTable.setStatus("1");
        inHospitalTableMapper.insert(inHospitalTable);
    }

    @Override
    public List<InHospitalTable> getInHospitalTableByTelephone(String telephone) {
        InHospitalTableExample inHospitalTableExample = new InHospitalTableExample();
        InHospitalTableExample.Criteria criteria = inHospitalTableExample.createCriteria();
        criteria.andTelephoneEqualTo(telephone);
        List<InHospitalTable> inHospitalTableList = inHospitalTableMapper.selectByExample(inHospitalTableExample);


        return inHospitalTableList;
    }
}
