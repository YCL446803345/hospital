package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.LeaveHospital;

public interface LeaveHospitalService {

    //分页模糊查询出院申请
    PageInfo<LeaveHospital> queryLeaveHospital(LeaveHospital leaveHospital, Integer pageNum, Integer pageSize);

    //撤销出院
    void gotoCancelLeaveHospitalById(Integer id);
    //申请出院
    void addLeaveHospital(LeaveHospital leaveHospital);
}
