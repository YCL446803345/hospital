package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Cost;
import com.woniu.entity.PaymentRecord;

import java.util.List;

public interface HospitalizationBillServer {
    //生成住院账单
    void addHospitalizationBill(Integer patientId,float money);

    //提前缴费
    Float updateHospitalizationBill (Integer id);

    Cost query(int id);

    PageInfo<Cost> queryAll (String name, String no, Integer pageNum, Integer pageSize);

    PageInfo<Cost> queryAllOut (String name, String no, Integer pageNum, Integer pageSize);

    PageInfo<Cost> leaveHospital (String name, String no, Integer pageNum, Integer pageSize);

    void updateMoney (Integer id, Float money);

    List<PaymentRecord> queryPayment(Integer id);

    List<Integer> countHospitalization ( );

    List<Float> countInBill ( );

    List<Float> countOutBill ( );

    List<Float> queryPersonalData (Integer id);

    List<Float> queryPersonalTime (Integer id);

    List<Float> getAllPayCount (String startTime, String endTime);
}
