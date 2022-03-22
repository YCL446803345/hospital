package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Patient;
import com.woniu.entity.PatientExample;
import com.woniu.mapper.BedMapper;
import com.woniu.mapper.PatientMapper;
import com.woniu.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("patientService")
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private BedMapper bedMapper;

    @Override
    public PageInfo<Patient> findPatients(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Patient> patientList = patientMapper.findPatients(patient);
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

    @Override
    public void updateBed(Patient patient) {
        patientMapper.updateBed(patient);
        bedMapper.changeBedStatusOn(patient);
    }

    @Override
    public void updateDoctorOrNurse(Patient patient) {
        patientMapper.updateDoctorOrNurse(patient);
    }
}
