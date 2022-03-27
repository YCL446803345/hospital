package com.woniu.service;

import com.woniu.entity.Drug;

import java.util.List;

public interface DrugService {
    List<Drug> findAllDrug(Drug drug);

    void updateDrug(Drug drug);

    void addDrug(Drug drug);
    //获取所有药品列表
    List<Drug> findDrugList();
}
