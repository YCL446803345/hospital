package com.woniu.service.impl;

import com.woniu.entity.Patient;
import com.woniu.entity.Project;
import com.woniu.mapper.MedicalAdviceBillMapper;
import com.woniu.service.MedicalAdviceBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("medicalAdviceBillService")
public class MedicalAdviceBillServiceImpl implements MedicalAdviceBillService {
    @Autowired
    private MedicalAdviceBillMapper medicalAdviceBillMapper;

    @Override
    public List<Project> getMedicalAdviceBill(Patient patient) {
        return medicalAdviceBillMapper.getMedicalAdviceBill(patient);
    }
}
