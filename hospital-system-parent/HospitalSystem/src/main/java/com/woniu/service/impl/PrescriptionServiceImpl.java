package com.woniu.service.impl;

import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;
import com.woniu.mapper.PrescriptionMapper;
import com.woniu.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处方业务层
 */
@Service("prescriptionService")
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;


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


}
