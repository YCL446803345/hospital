package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Cost;

import java.util.List;

public interface HospitalizationBillServer {
    //生成住院账单
    void addHospitalizationBill(Integer patientId,float money);

    //提前缴费
    Cost updateHospitalizationBill (Integer id);

    Cost query(int id);

    PageInfo<Cost> queryAll (String name, String no, Integer pageNum, Integer pageSize);

    PageInfo<Cost> queryAllOut (String name, String no, Integer pageNum, Integer pageSize);

    PageInfo<Cost> leaveHospital (String name, String no, Integer pageNum, Integer pageSize);

    void updateMoney (Integer id, Float money);
}
