package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Bed;
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

    /**
     * 修改非待床病人的床位
     * @param patient
     */
    @Override
    public void updateBed(Patient patient) {
        patientMapper.updateBed(patient);
        bedMapper.changeBedStatusOn(patient);
    }

    @Override
    public void updateDoctorOrNurse(Patient patient) {
        patientMapper.updateDoctorOrNurse(patient);
    }



    @Override
    public void updateBalance (Float o,Integer id) {
        Patient patient = patientMapper.selectByPrimaryKey(id);
        patient.setBalance(patient.getBalance() + o);
        patientMapper.updateByPrimaryKeySelective(patient);

    }
    @Override
    public PageInfo<Patient> findPatientsWithNotBed(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Patient> patientList = patientMapper.findPatientsWithNotBed(patient);
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

    /**
     * 给待床病人安排床位，同时修改床位状态
     * @param patient
     */
    @Override
    public void updateBedByPatientWithOutBed(Patient patient) {
        patientMapper.updateBedByPatientWithOutBed(patient);
        bedMapper.changeBedStatusOn(patient);
    }

    /**
     * 终止使用床位
     * @param patient
     */
    @Override
    public void stopUseBed(Patient patient) {
        patientMapper.stopUseBed(patient);
        bedMapper.changeBedStatusOff(patient);

    }


    //分页模糊查询患者列表
    public PageInfo<Patient> queryPatient(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Patient> patientList = patientMapper.queryPatientList(patient);
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

}

