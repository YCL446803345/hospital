package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.DrugOut;
import com.woniu.entity.DrugOutDrug;
import com.woniu.entity.WithdrawalDrug;
import com.woniu.service.DrugOutDrugService;
import com.woniu.service.DrugOutService;
import com.woniu.service.DrugService;
import com.woniu.service.WithdrawalDrugService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class OutDrugController {

    @Autowired
    private DrugOutService drugOutService;
    @Autowired
    private DrugOutDrugService drugOutDrugService;
    @Autowired
    private DrugService drugService;
    @Autowired
    private WithdrawalDrugService withdrawalDrugService;

    //退药列表
    @GetMapping("drug/outDrugList")
    @PreAuthorize("hasAnyAuthority('drug:prescriptionList')")
    public ResponseResult<PageInfo<DrugOut>> findAllDrug(String doctorName, String preName,
                                                         @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                         @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DrugOut> drugOutList = drugOutService.findAllDrug(doctorName,preName);
        PageInfo drugPageInfo = new PageInfo(drugOutList);
        return new ResponseResult<PageInfo<DrugOut>>(drugPageInfo,"OK",200);
    }

    //根据退药单id查询退了什么药品和数量
    @GetMapping("drug/outDrug")
    public ResponseResult<List<DrugOutDrug>> getByIdDrugs(Integer pid){
        List<DrugOutDrug> drugOutDrugList = drugOutDrugService.getByIdDrugs(pid);
        return new ResponseResult<>(drugOutDrugList,"OK",200);
    }

    //单个退药单退药
    @PostMapping("drug/single/outDrug")
    @PreAuthorize("hasAnyAuthority('drug:returnDrug')")
    public ResponseResult updateDrugNumById(@RequestParam("durgStr") String durgStr,
                                            @RequestParam("account") String account,
                                            @RequestParam("did") Integer did){

        String[] split = durgStr.split(",");
        drugService.batchUpdateStock(split,account,did);
        return ResponseResult.ok();
    }

    //批量退药、改状态、加库存、批量添加退药记录
    @PostMapping("/drug/outDrug")
    @PreAuthorize("hasAnyAuthority('drug:returnDrug')")
    public ResponseResult updateBatchNumByIds(@RequestParam("idStrs") String idStrs,@RequestParam("account") String account){
        String[] split = idStrs.split(",");
        List<Integer> ids = new ArrayList<>();
        for (String idStr : split) {
            ids.add(Integer.parseInt(idStr));
        }
        drugService.batchUpdate(ids,account);
        return ResponseResult.ok();
    }

    //查询所有退药记录
    @GetMapping("drug/findAllOutDrugRecord")
    @PreAuthorize("hasAnyAuthority('drug:sendOut')")
    public ResponseResult<PageInfo<WithdrawalDrug>> findAllDrugRecord(String name,
                                                                   @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                                   @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<WithdrawalDrug> drugOutList = withdrawalDrugService.findAllRecord(name);
        PageInfo drugPageInfo = new PageInfo(drugOutList);
        return new ResponseResult<PageInfo<WithdrawalDrug>>(drugPageInfo,"OK",200);
    }

    //查询退药的名称和数量
    @GetMapping("drug/getDrugByOutDrugId")
    public ResponseResult<List<DrugOutDrug>> getDrugByOutDrugId(Integer pid){
        List<DrugOutDrug> byIdDrugs = drugOutDrugService.getByIdDrugs(pid);
        return new ResponseResult<List<DrugOutDrug>>(byIdDrugs,"OK",200);
    }
}
