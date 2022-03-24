package com.woniu.service;

import com.woniu.entity.SendDrugRecord;

import java.util.List;

public interface SendDrugRecordService {
    List<SendDrugRecord> findAllSendDrug(String name);
}
