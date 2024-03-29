package com.woniu.service;


import com.github.pagehelper.PageInfo;
import com.woniu.entity.Prescription;


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 处方接口
 */
public interface PrescriptionService {

    List<Prescription> getPresDrugByMany(String doctorName,String nurseName,String preName);

    void updateStatusById(Integer pid, String account);

    List<LinkedHashSet<String>> updateByBatch(List<String> newlist, String account);

    Prescription getDrugNameAndNumAndCreateTime(Integer pid);



    PageInfo<Prescription> getPrescriptions(Prescription prescription, Integer pageNum, Integer pageSize);

    void checkPrescription(Integer id);

    void doPrescription(Integer id);

    void stopPrescription(Integer id);

    //分页模糊查询处方列表
    PageInfo<Prescription> queryPrescriptionList(Prescription prescription, Integer pageNum, Integer pageSize);

    //处方下达
    void addPrescription(Prescription prescription);

    //撤销处方
    void gotoStopPrescriptionById(Integer id);
}
