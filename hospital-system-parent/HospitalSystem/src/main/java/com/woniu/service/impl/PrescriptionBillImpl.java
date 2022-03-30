package com.woniu.service.impl;

import com.woniu.entity.Drug;
import com.woniu.entity.Patient;
import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionBill;
import com.woniu.mapper.PrescriptionBillMapper;
import com.woniu.mapper.PrescriptionMapper;
import com.woniu.service.PrescriptionBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("prescriptionBillService")
public class PrescriptionBillImpl implements PrescriptionBillService {

    @Autowired
    private PrescriptionBillMapper prescriptionBillMapper;

    @Override
    public List<Drug> getPrescriptionBill(Patient patient) {
        List<Drug> drugs= prescriptionBillMapper.getPrescriptionBill(patient);
        for (Drug drug : drugs) {
            drug.setMoney(drug.getSalePrice() * drug.getNum());
        }
        return drugs;
    }

    @Override
    public Double getPrescriptionBillHavePay(Patient patient) {
        List<Drug> drugs=prescriptionBillMapper.getPrescriptionBillHavePay(patient);
        Double num=0.0;
        for (Drug drug : drugs) {
            num+=drug.getSalePrice()*drug.getNum();
        }
        return num;
    }
}
