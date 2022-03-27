package com.woniu.service;

import com.woniu.entity.Drug;

import java.util.List;

public interface DrugService {
    List<Drug> findAllDrug(Drug drug);

    void updateDrug(Drug drug);

    void addDrug(Drug drug);

    List<Drug> getDrugsByPrescriptionId(Integer id);
}
