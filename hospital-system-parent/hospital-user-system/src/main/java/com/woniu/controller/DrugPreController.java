package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;
import com.woniu.service.PrescriptionDrugService;
import com.woniu.service.PrescriptionService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 药房处方控制类
 */
@RestController
public class DrugPreController {

    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private PrescriptionDrugService prescriptionDrugService;


    //查询处方列表+分页+模糊查询
    @GetMapping("drug/prescription")
    public ResponseResult<PageInfo<Prescription>> findAllPrescription(String doctorName,String status,
                                                                      Date startTime, Date endTime,
                                                                      @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                                      @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Prescription> preDrugList = prescriptionService.getPresDrugByMany(doctorName,status,startTime,endTime);
        PageInfo preDrugPageInfo = new PageInfo(preDrugList);
        return new ResponseResult<PageInfo<Prescription>>(preDrugPageInfo,"OK",200);
    }

    //通过处方id查找该处方下所有药品及数量
    @GetMapping("drug/getDrugByPreId")
    public ResponseResult<List<PrescriptionDrug>> findDrugNameAndNum(Integer pid){
        List<PrescriptionDrug> prescriptionDrugList =  prescriptionDrugService.getDrugNameAndNumByPreId(pid);
        return new ResponseResult<List<PrescriptionDrug>>(prescriptionDrugList,"OK",200);
    }

    //药房撤销处方同时记录撤销人和当前撤销时间
    @PostMapping("drug/updatePreStatus")
    public ResponseResult updatePreDrugStatus(@RequestParam("pid") Integer pid,@RequestParam("account") String account){
        prescriptionService.updateStatusById(pid,account);
        return ResponseResult.ok();
    }

    //通过处方id配药,支持批量配药
    //同时添加发药记录
    //添加发药账单记录
    @PostMapping("drug/sendDrug")
    public ResponseResult updatePreDrugStatusAndAddSendDrug(@RequestParam("idStrs") String idStrs,@RequestParam("account") String account){
        List<String> list = Arrays.asList(idStrs.split(","));
        List<String> newlist = new ArrayList<String>(list);
        prescriptionService.updateByBatch(newlist,account);
        return ResponseResult.ok();
    }

}
