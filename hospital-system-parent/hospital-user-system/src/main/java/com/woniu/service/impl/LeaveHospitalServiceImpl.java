package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;
import com.woniu.entity.LeaveHospital;
import com.woniu.mapper.LeaveHospitalMapper;
import com.woniu.service.LeaveHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveHospitalServiceImpl implements LeaveHospitalService {
    @Autowired
    private LeaveHospitalMapper leaveHospitalMapper;

    //分页模糊查询出院申请列表
    public PageInfo<LeaveHospital> queryLeaveHospital(LeaveHospital leaveHospital, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LeaveHospital> leaveHospitalList = leaveHospitalMapper.queryLeaveHospitalList(leaveHospital);
        PageInfo<LeaveHospital> leaveHospitalPageInfo = new PageInfo<>(leaveHospitalList);
        return leaveHospitalPageInfo;
    }
}
