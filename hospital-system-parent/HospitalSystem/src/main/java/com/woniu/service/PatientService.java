package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Patient;

import java.util.List;

public interface PatientService {
    PageInfo<Patient> findPatients(Patient patient, Integer pageNum, Integer pageSize);

    void updateBed(Patient patient);

    void updateDoctorOrNurse(Patient patient);

    //医生站分页模糊查询患者列表
    PageInfo<Patient> queryPatient(Patient patient, Integer pageNum, Integer pageSize);
}
