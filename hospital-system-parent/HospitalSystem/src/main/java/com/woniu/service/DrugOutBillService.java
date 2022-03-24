package com.woniu.service;

import com.woniu.entity.Drug;
import com.woniu.entity.Patient;

import java.util.List;

public interface DrugOutBillService {
    List<Drug> getDrugOutBill(Patient patient);
}
