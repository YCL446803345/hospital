package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;
import com.woniu.entity.LeaveHospital;
import com.woniu.entity.Patient;
import com.woniu.mapper.LeaveHospitalMapper;
import com.woniu.mapper.PatientMapper;
import com.woniu.service.LeaveHospitalService;
import com.woniu.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeaveHospitalServiceImpl implements LeaveHospitalService {
    @Autowired
    private LeaveHospitalMapper leaveHospitalMapper;
    @Autowired
    private PatientMapper patientMapper;


    //分页模糊查询出院申请列表
    public PageInfo<LeaveHospital> queryLeaveHospital(LeaveHospital leaveHospital, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LeaveHospital> leaveHospitalList = leaveHospitalMapper.queryLeaveHospitalList(leaveHospital);
        PageInfo<LeaveHospital> leaveHospitalPageInfo = new PageInfo<>(leaveHospitalList);
        return leaveHospitalPageInfo;
    }

    //撤销出院
    public void gotoCancelLeaveHospitalById(Integer id) {
        leaveHospitalMapper.cancelLeaveHospitalById(id);
    }

    //申请出院
    public void addLeaveHospital(LeaveHospital leaveHospital) {
        leaveHospital.setLeaveStatus(2);
        leaveHospital.setLeaveTime(new Date());
        leaveHospitalMapper.addLeaveHospital(leaveHospital);

        Patient patient = new Patient();
        patient.setId(leaveHospital.getId());
        patientMapper.updateStatusBy(patient);

    }
}
