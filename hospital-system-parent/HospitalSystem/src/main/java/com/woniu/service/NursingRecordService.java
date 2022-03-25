package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.NursingRecord;

public interface NursingRecordService {
    void addNursingRecord(NursingRecord nursingRecord);

    PageInfo<NursingRecord> findNursingRecordsByPatientId(NursingRecord nursingRecord, Integer pageNum, Integer pageSize);

    void deleteNursingRecord(Integer id);

    void updateNursingRecord(NursingRecord nursingRecord);
}
