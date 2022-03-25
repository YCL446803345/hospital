package com.woniu.service;


import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 处方接口
 */
public interface PrescriptionService {

    List<Prescription> getPresDrugByMany(String doctorName,String status, Date startTime, Date endTime);

    void updateStatusById(Integer pid, String account);

    LinkedHashSet<String> updateByBatch(List<String> newlist, String account);

    Prescription getDrugNameAndNumAndCreateTime(Integer pid);
}
