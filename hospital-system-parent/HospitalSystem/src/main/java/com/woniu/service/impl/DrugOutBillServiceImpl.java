package com.woniu.service.impl;

import com.woniu.entity.Drug;
import com.woniu.entity.Patient;
import com.woniu.mapper.DrugOutBillMapper;
import com.woniu.service.DrugOutBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("drugOutBillService")
public class DrugOutBillServiceImpl implements DrugOutBillService {
    @Autowired
    private DrugOutBillMapper drugOutBillMapper;

    @Override
    public List<Drug> getDrugOutBill(Patient patient) {
        List<Drug> drugs=drugOutBillMapper.getDrugOutBill(patient);
        for (Drug drug : drugs) {
            drug.setMoney(drug.getSalePrice() * drug.getNum());
        }
        return drugs;
    }
}
