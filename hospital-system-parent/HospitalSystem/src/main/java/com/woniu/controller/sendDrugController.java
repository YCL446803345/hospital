package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Prescription;
import com.woniu.entity.SendDrugRecord;
import com.woniu.service.SendDrugRecordService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 发药记录
 */
@RestController
public class sendDrugController {

    @Autowired
    private SendDrugRecordService sendDrugRecordService;

    //查询发药记录+分页+条件查询
    @GetMapping("drug/findAllSendDrug")
    @PreAuthorize("hasAnyAuthority('drug:sendOut')")
    public ResponseResult<PageInfo<SendDrugRecord>> findAllPrescription(String name,
                                                                        @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                                        @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SendDrugRecord> sendDrugRecordList = sendDrugRecordService.findAllSendDrug(name);
        PageInfo sendDrugPageInfo = new com.github.pagehelper.PageInfo(sendDrugRecordList);
        return new ResponseResult<PageInfo<SendDrugRecord>>(sendDrugPageInfo,"OK",200);
    }

}
