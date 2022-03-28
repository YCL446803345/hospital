package com.woniu.service;

import com.woniu.entity.DrugOut;

import java.util.List;

public interface DrugOutService {
    List<DrugOut> findAllDrug(String doctorName, String preName);
}
