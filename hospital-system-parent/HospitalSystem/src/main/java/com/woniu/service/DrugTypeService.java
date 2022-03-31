package com.woniu.service;

import com.woniu.entity.DrugType;

import java.util.List;

public interface DrugTypeService {
    List<DrugType> findAll();

    void add(DrugType drugType);
}
