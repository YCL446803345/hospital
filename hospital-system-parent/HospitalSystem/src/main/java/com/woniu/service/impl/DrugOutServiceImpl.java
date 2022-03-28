package com.woniu.service.impl;


import com.woniu.entity.DrugOut;
import com.woniu.mapper.DrugOutMapper;
import com.woniu.service.DrugOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("drugOutService")
public class DrugOutServiceImpl implements DrugOutService {

    @Autowired
    private DrugOutMapper drugOutMapper;

    //退药列表查询+分页+条件
    public List<DrugOut> findAllDrug(String doctorName, String preName) {
        List<DrugOut> drugOutList = drugOutMapper.findAllDrugList(doctorName,preName);
        return drugOutList;
    }
}
