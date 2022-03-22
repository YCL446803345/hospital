package com.woniu.service.impl;

import com.woniu.entity.Prescription;
import com.woniu.mapper.PrescriptionMapper;
import com.woniu.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处方业务层
 */
@Service("prescriptionService")
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    //药房查询处方
    public List<Prescription> findAllPre(Prescription prescription) {

        return null;
    }

    //医生添加处方申请


}
