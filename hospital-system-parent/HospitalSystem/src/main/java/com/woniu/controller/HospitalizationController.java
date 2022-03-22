//package com.woniu.controller;
//
//import com.woniu.service.HospitalizationBillServer;
//import com.woniu.util.ResponseResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class HospitalizationController {
//
//    @Autowired
//    private HospitalizationBillServer hospitalizationBillServer;
//
//    /** 生成住院账单
//     * @param patientId 病人id
//     * @param money 病人余额
//     */
//    @PostMapping("addHospitalizationBill")
//    public ResponseResult addHospitalizationBill(Integer patientId,float money){
//        hospitalizationBillServer.addHospitalizationBill(patientId, money);
//        return new ResponseResult(200,"OK");
//    }
//
//
//    @PostMapping("advancePayment")
//    public ResponseResult advancePayment(Integer id,float money){
//        hospitalizationBillServer.updateHospitalizationBill(id,money);
//        return new ResponseResult(200,"OK");
//    }
//}
