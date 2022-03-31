package com.woniu.controller;

import com.woniu.entity.InHospitalTable;
import com.woniu.service.InHospitalTableService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 病人管理
 */
@RestController
public class InHospitalTableController {
    @Autowired
    private InHospitalTableService inHospitalTableService;


    //添加病人预约
    @RequestMapping("inHospitalTable/add")
//    @PreAuthorize("hasAnyAuthority('inHospitalTable:add')")
    public ResponseResult<String> addInHospitalTableService(@RequestBody InHospitalTable inHospitalTable){
        InHospitalTable cardId=inHospitalTableService.getByCardId(inHospitalTable.getCardId());
        if (cardId != null) {
            return new ResponseResult<String>(2008,"身份证已存在");
        }


        inHospitalTableService.addInHospitalTableService(inHospitalTable);
        return new ResponseResult<String>(200,"添加成功");
    }

    //根据手机号码查询病人预约信息
    @RequestMapping("inHospitalTable/getByTelephone")
    public ResponseResult<List<InHospitalTable>> getInHospitalTableByTelephone(String telephone){
        List<InHospitalTable> inHospitalTableList=inHospitalTableService.getInHospitalTableByTelephone(telephone);

        return new ResponseResult<List<InHospitalTable>>(inHospitalTableList,"OK",200);
    }

}
