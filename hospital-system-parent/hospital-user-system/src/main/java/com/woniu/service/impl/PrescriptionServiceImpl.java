package com.woniu.service.impl;

import com.woniu.entity.*;
import com.woniu.mapper.PrescriptionMapper;
import com.woniu.mapper.SendDrugRecordMapper;
import com.woniu.mapper.WorkerMapper;
import com.woniu.service.PrescriptionService;
import com.woniu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处方业务层
 */
@Service("prescriptionService")
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    private SendDrugRecordMapper sendDrugRecordMapper;
    @Autowired
    private WorkerMapper workerMapper;


    //处方详情+模糊查询
    public List<Prescription> getPresDrugByMany(String doctorName, String status, Date startTime, Date endTime) {

        List<Prescription> prescriptionList2= prescriptionMapper.getPreDrugByMany(doctorName,status,startTime,endTime);

        //相同处方排除法
        for (int i = 0; i < prescriptionList2.size(); i++) {
            for (int j = 0; j < prescriptionList2.size(); j++) {
                if(i != j && prescriptionList2.get(i).getId()== prescriptionList2.get(j).getId()) {
                    prescriptionList2.remove(prescriptionList2.get(j));
                }
            }
        }
        return prescriptionList2;
    }

    //药方撤销药方修改状态
    public void updateStatusById(Integer pid, String account) {
        Prescription prescription = new Prescription();
        prescription.setId(pid);
        prescription.setPrescriptionStatus(3);
        prescription.setSpare1(account);    //撤销人
        prescription.setSpare2(TimeUtil.getNowTime(new Date())); //撤销时间
        prescriptionMapper.updateByPrimaryKeySelective(prescription);
    }


    //药房批量修改处方状态，添加数据
    public void updateByBatch(List<String> newlist, String account) {

        Worker worker = workerMapper.selectWorkerByAccount(account);
        //药房批量修改处方状态
        List<Prescription> prescriptionList = new ArrayList<>();
        for (String s : newlist) {
            int i = Integer.parseInt(s);

            Prescription prescription = new Prescription();
            prescription.setId(i);
            prescription.setPrescriptionStatus(2);
            prescription.setSpare1(worker.getName());
            prescription.setSpare2(TimeUtil.getNowTime(new Date()));
            prescriptionList.add(prescription);
        }
        for (Prescription prescription : prescriptionList) {
            prescriptionMapper.updateByPrimaryKeySelective(prescription);
        }

        //药房批量添加发药记录
        List<SendDrugRecord> sendDrugRecordList = new ArrayList<>();
        for (String s : newlist) {
            int pid = Integer.parseInt(s);
            SendDrugRecord sendDrugRecord = new SendDrugRecord();
            sendDrugRecord.setPrescriptionId(pid);
            sendDrugRecord.setSendDrugTime(new Date());
            sendDrugRecord.setSpare1(worker.getName());
            sendDrugRecordList.add(sendDrugRecord);
        }
        sendDrugRecordMapper.addSenDrugBatch(sendDrugRecordList);

    }
}
