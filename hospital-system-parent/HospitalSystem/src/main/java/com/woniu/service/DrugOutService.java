package com.woniu.service;

import com.woniu.entity.DrugOut;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.DrugOut;
import java.util.List;

public interface DrugOutService {
    List<DrugOut> findAllDrug(String doctorName, String preName);


    //分页模糊查询退药列表
    PageInfo<DrugOut> queryDrugOutList(DrugOut drugOut, Integer pageNum, Integer pageSize);
    //申请退药
    void addDrugOut(DrugOut drugOut);


    //撤销退药
    void gotoStopDrugOutById(DrugOut drugOut);

}
