package com.woniu.service.impl;

import com.woniu.entity.SendDrugRecord;
import com.woniu.entity.SendDrugRecordExample;
import com.woniu.mapper.SendDrugRecordMapper;
import com.woniu.service.PrescriptionDrugService;
import com.woniu.service.SendDrugRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 发药记录表业务
 */
@Service("sendDrugRecordService")
public class SendDrugRecordServiceImpl implements SendDrugRecordService {

    @Autowired
    private SendDrugRecordMapper sdrMapper;
    @Autowired
    private PrescriptionDrugService prescriptionDrugService;

    //查询发药记录
    public List<SendDrugRecord> findAllSendDrug(String name) {
        List<SendDrugRecord> sendDrugRecordList =  sdrMapper.findAllSendRecord(name);
        return sendDrugRecordList;
    }

}
