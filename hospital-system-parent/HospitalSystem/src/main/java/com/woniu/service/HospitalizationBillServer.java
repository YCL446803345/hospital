package com.woniu.service;

public interface HospitalizationBillServer {
    //生成住院账单
    void addHospitalizationBill(Integer patientId,float money);

    //提前缴费
    void updateHospitalizationBill (Integer id, float money);
}
