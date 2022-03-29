package com.woniu.service;

import com.woniu.entity.Patient;
import com.woniu.entity.Project;

import java.util.List;

public interface MedicalAdviceBillService {
    List<Project> getMedicalAdviceBill(Patient patient);

    Double getMedicalAdviceBillHavePay(Patient patient);
}
