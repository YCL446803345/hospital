package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.LeaveHospital;

public interface LeaveHospitalService {

    PageInfo<LeaveHospital> queryLeaveHospital(LeaveHospital leaveHospital, Integer pageNum, Integer pageSize);
}
