package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;
import com.woniu.entity.Prescription;
import com.woniu.service.DrugService;
import com.woniu.service.PrescriptionService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药品信息
 */
@RestController
public class DrugInfoController {

    @Autowired
    private DrugService drugService;
    @Autowired
    private PrescriptionService prescriptionService;

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

    //查询处方列表+分页+模糊查询
    @PostMapping("drug/prescription")
    public ResponseResult<PageInfo<Prescription>> findAllPrescription(Prescription prescription,
                                                                      @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                                      @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Prescription> prescriptionList = prescriptionService.findAllPre(prescription);
        PageInfo prePageInfo = new PageInfo(prescriptionList);
        return new ResponseResult<PageInfo<Prescription>>(prePageInfo,"OK",200);
    }

    //配药 - 库存 + 发药记录
//    @PostMapping("drug/sendDrug")
//    public ResponseResult sendDrug(Integer doctorId,){
//
//    }

}
