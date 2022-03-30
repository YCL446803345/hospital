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
//        List<Prescription> prescriptions=prescriptionBillMapper.getPrescriptionByPatientId(patient);
//
//        ArrayList<PrescriptionBill> list = new ArrayList<>();
//        for (Prescription prescription : prescriptions) {
//            PrescriptionBill prescriptionBill=prescriptionBillMapper.getPrescriptionBillByPrescriptionId(prescriptionBill.getPrescriptionId());
//            list.add(prescriptionBill);
//        }
//
//        for (PrescriptionBill o : list) {
//            if(o.getStatus().equals(2)){
//                Drug drugs=prescriptionBillMapper.getDrugByprescriptionBillId(o.getPrescriptionId());
//
//            }
//
//        }

        return null;
    }
}
