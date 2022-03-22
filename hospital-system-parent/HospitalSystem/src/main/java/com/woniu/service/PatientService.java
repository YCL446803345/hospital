package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Bed;
import com.woniu.entity.Patient;

import java.util.List;

public interface PatientService {
    PageInfo<Patient> findPatients(Patient patient, Integer pageNum, Integer pageSize);

    void updateBed(Patient patient);

    void updateDoctorOrNurse(Patient patient);

    PageInfo<Patient> findPatientsWithNotBed(Patient patient, Integer pageNum, Integer pageSize);

    void updateBedByPatientWithOutBed(Patient patient);

    void stopUseBed(Patient patient);
}
