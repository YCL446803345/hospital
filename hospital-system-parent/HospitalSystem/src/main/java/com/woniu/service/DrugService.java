package com.woniu.service;

import com.woniu.entity.Drug;

import java.util.List;

public interface DrugService {
    List<Drug> findAllDrug(Drug drug);

    void updateDrug(Drug drug);

    void addDrug(Drug drug);


    Drug getDrugById(Integer id);

    List<Drug> findAllDownDrug(Drug drug);

    List<String> finAllType();

    void update(Drug updateDrug);

    void updateByIdBatchStatus(List<String> strings);

    List<Drug> getDrugsByPrescriptionId(Integer id);

}
