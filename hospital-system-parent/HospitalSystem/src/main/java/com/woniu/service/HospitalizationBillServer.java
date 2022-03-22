package com.woniu.service;

import java.util.List;

public interface HospitalizationBillServer {
    //生成住院账单
    void addHospitalizationBill(Integer patientId,float money);

    //提前缴费
    List<Object> updateHospitalizationBill (Integer id, float money);

    List<Object> query(int id);

    List<List> queryAll ( );

    List<List> queryAllOut ( );

    List<List> leaveHospital ();
}
