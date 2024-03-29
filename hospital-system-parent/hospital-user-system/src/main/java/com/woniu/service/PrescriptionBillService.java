package com.woniu.service;

import com.woniu.entity.Drug;
import com.woniu.entity.Patient;
import com.woniu.entity.PrescriptionBill;

import java.util.List;

public interface PrescriptionBillService {
    List<Drug> getPrescriptionBill(Patient patient);
}
