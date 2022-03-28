package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.DrugOut;
import com.woniu.entity.DrugOutDrug;
import com.woniu.entity.Prescription;
import com.woniu.mapper.DrugOutDrugMapper;
import com.woniu.mapper.DrugOutMapper;
import com.woniu.service.DrugOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DrugOutServiceImpl implements DrugOutService {

    @Autowired
    private DrugOutMapper drugOutMapper;
    @Autowired
    private DrugOutDrugMapper drugOutDrugMapper;


    //分页模糊查询退药记录
    public PageInfo<DrugOut> queryDrugOutList(DrugOut drugOut, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DrugOut> drugOutList = drugOutMapper.queryDrugOutList(drugOut);
        PageInfo<DrugOut> drugOutPageInfo = new PageInfo<>(drugOutList);
        return drugOutPageInfo;
    }

    //申请退药
    public void addDrugOut(DrugOut drugOut) {
        drugOut.setCreateTime(new Date());
        drugOut.setOutStatus(1);
        drugOut.setOutCause("需退药");
        drugOutMapper.addDrugOut(drugOut);//新增退药申请记录

        DrugOutDrug drugOutDrug = new DrugOutDrug();
        drugOutDrug.setDrugId(drugOut.getDrugId());
        drugOutDrug.setDrugOutId(drugOut.getId());
        drugOutDrug.setNum(drugOut.getNum());
        drugOutDrugMapper.addDrugOutDrug(drugOutDrug);//新增退药药品中间表数据




    }

    //撤销退药
    public void gotoStopDrugOutById(DrugOut drugOut) {
        drugOutMapper.gotoStopDrugOutById(drugOut);
    }



}
