package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;
import com.woniu.entity.DrugOut;
import com.woniu.service.DrugOutService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutDrugController {

    @Autowired
    private DrugOutService drugOutService;

    //退药列表
    @GetMapping("drug/outDrugList")
    public ResponseResult<PageInfo<DrugOut>> findAllDrug(String doctorName, String preName,
                                                         @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                         @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DrugOut> drugOutList = drugOutService.findAllDrug(doctorName,preName);
        PageInfo drugPageInfo = new PageInfo(drugOutList);
        return new ResponseResult<PageInfo<DrugOut>>(drugPageInfo,"OK",200);
    }
}
