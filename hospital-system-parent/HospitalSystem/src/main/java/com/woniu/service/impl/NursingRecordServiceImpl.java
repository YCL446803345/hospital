package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.NursingRecord;
import com.woniu.entity.Patient;
import com.woniu.mapper.NursingRecordMapper;
import com.woniu.service.NursingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("nursingRecordService")
public class NursingRecordServiceImpl implements NursingRecordService {
    @Autowired
    private NursingRecordMapper nursingRecordMapper;

    @Override
    public void addNursingRecord(NursingRecord nursingRecord) {
        nursingRecord.setCreateTime(new Date());
        nursingRecordMapper.addNursingRecord(nursingRecord);
    }

    @Override
    public PageInfo<NursingRecord> findNursingRecordsByPatientId(NursingRecord nursingRecord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<NursingRecord> nursingRecords = nursingRecordMapper.findNursingRecordsByPatientId(nursingRecord);
        PageInfo<NursingRecord> patientPageInfo = new PageInfo<>(nursingRecords);
        return patientPageInfo;
    }
}
