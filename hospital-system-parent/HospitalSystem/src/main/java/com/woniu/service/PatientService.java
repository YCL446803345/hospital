package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.HospitalizationBill;
import com.woniu.entity.Patient;


public interface PatientService {
    PageInfo<Patient> findPatients(Patient patient, Integer pageNum, Integer pageSize);

    void updateBed(Patient patient);

    void updateDoctorOrNurse(Patient patient);



    void updateBalance (Float o,Integer id);

    PageInfo<Patient> findPatientsWithNotBed(Patient patient, Integer pageNum, Integer pageSize);

    void updateBedByPatientWithOutBed(Patient patient);

    void stopUseBed(Patient patient);


    //医生站分页模糊查询患者列表
    PageInfo<Patient> queryPatient(Patient patient, Integer pageNum, Integer pageSize);

    PageInfo<Patient> findPatientsByChangeDept(Patient patient, Integer pageNum, Integer pageSize);

    void updatePatientDept(Patient patient);

    void updatePatient(Patient patient);

    HospitalizationBill getHospitalizationBill(Patient patient);

}
