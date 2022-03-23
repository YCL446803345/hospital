package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;
import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;
import com.woniu.service.DrugService;
import com.woniu.service.PrescriptionService;
import com.woniu.util.ResponseResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 药品信息
 */
@RestController
public class DrugInfoController {

    @Autowired
    private DrugService drugService;


    //查询所有药品信息
    @GetMapping("drug/findAllDrug")
    public ResponseResult<PageInfo<Drug>> findAllDrug(Drug drug,
                                                  @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                  @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Drug> drugList = drugService.findAllDrug(drug);
        PageInfo drugPageInfo = new PageInfo(drugList);
        return new ResponseResult<PageInfo<Drug>>(drugPageInfo,"OK",200);
    }

    //修改药品信息
    @PostMapping("drug/updateDrugStatus")
    public ResponseResult updateDrugStatus(Drug drug){
        drugService.updateDrug(drug);
        return ResponseResult.ok();
    }

    //添加药品
    @PostMapping("drug/addDrug")
    public ResponseResult addDrug(Drug drug){
        drugService.addDrug(drug);
        return ResponseResult.ok();
    }


}
