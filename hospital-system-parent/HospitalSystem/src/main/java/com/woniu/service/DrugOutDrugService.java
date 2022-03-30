package com.woniu.service;

import com.woniu.entity.DrugOutDrug;

import java.util.List;

public interface DrugOutDrugService {
    List<DrugOutDrug> getByIdDrugs(Integer pid);
}
