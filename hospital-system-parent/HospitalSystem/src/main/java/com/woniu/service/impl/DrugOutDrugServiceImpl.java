package com.woniu.service.impl;

import com.woniu.entity.DrugOutDrug;
import com.woniu.mapper.DrugOutDrugMapper;
import com.woniu.service.DrugOutDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("drugOutDrugService")
public class DrugOutDrugServiceImpl implements DrugOutDrugService {

    @Autowired
    private DrugOutDrugMapper drugOutDrugMapper;

    //根据id查药品和数量
    public List<DrugOutDrug> getByIdDrugs(Integer pid) {
        List<DrugOutDrug> drugOutDrugList = drugOutDrugMapper.getByIdDrugs(pid);
        return drugOutDrugList;
    }
}
