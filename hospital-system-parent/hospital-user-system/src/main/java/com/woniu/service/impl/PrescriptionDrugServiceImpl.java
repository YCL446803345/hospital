package com.woniu.service.impl;


import com.woniu.entity.PrescriptionDrug;
import com.woniu.mapper.PrescriptionDrugMapper;
import com.woniu.service.PrescriptionDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处方和药品关联业务
 */
@Service("prescriptionDrugService")
public class PrescriptionDrugServiceImpl implements PrescriptionDrugService {

    @Autowired
    private PrescriptionDrugMapper prescriptionDrugMapper;

    //通过处方id查找该处方下所有药品及数量
    public List<PrescriptionDrug> getDrugNameAndNumByPreId(Integer pid) {
        List<PrescriptionDrug> prescriptionDrugList = prescriptionDrugMapper.getDrugNameAndNumByPreId(pid);
        return prescriptionDrugList;
    }

}
