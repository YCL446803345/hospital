package com.woniu.controller;

import com.woniu.service.HospitalizationBillServer;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HospitalizationController {

    @Autowired
    private HospitalizationBillServer hospitalizationBillServer;

    /** 生成住院账单
     * @param patientId 病人id
     * @param money 病人余额
     */
    @PostMapping("addHospitalizationBill")
    public ResponseResult addHospitalizationBill(Integer patientId,float money){
        hospitalizationBillServer.addHospitalizationBill(patientId, money);
        return new ResponseResult(200,"OK");
    }


    /** 缴费并返回病人住院所花费的费用
     * @param id 根据id去搜索住院的病人
     * @param money 缴费的费用
     * @return 返回值中有五个数 , 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额
     */
    @PostMapping("advancePayment")
    public ResponseEntity<List<Object>> advancePayment(Integer id, float money){
        List<Object> floats = hospitalizationBillServer.updateHospitalizationBill(id, money);
        return new ResponseEntity<List<Object>> (floats, HttpStatus.OK);
    }

    /** 按条件查询相关病人的相关数据
     * @param id 根据病人id查找响应的病人
     * @return 返回值中有九个值
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额
     * 第六个是退药集合 第七个是药品集合 第八个是住院天数 第九个是医嘱集合
     */
    @GetMapping("query")
    public ResponseEntity<List<Object>> query(int id){
        List<Object> query = hospitalizationBillServer.query(id);
        return new ResponseEntity<List<Object>> (query, HttpStatus.OK);
    }

    /** 查询所有在院病人所花费费用列表
     * @return 返回值是一个集合,每个集合中有六个数 ,
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额,第六个是病人id
     */
    @GetMapping("queryAllCost")
    public ResponseEntity<List<List>> queryAllCost(){
        List<List> lists = hospitalizationBillServer.queryAll();
        return new ResponseEntity<List<List>> (lists, HttpStatus.OK);
    }

    /** 查询所有已出院病人所花费费用列表
     * @return 返回值是一个集合,每个集合中有六个数 ,
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额,第六个是病人id
     */
    @GetMapping("queryAllOut")
    public ResponseEntity<List<List>> queryAllOut(){
        List<List> lists = hospitalizationBillServer.queryAllOut();
        return new ResponseEntity<List<List>> (lists, HttpStatus.OK);
    }

    /** 查询所有审核出院病人列表
     * @return
     */
    @GetMapping("leaveHospital")
    public ResponseEntity<List<List>> leaveHospital(){
        List<List> lists = hospitalizationBillServer.leaveHospital();
        return new ResponseEntity<List<List>> (lists, HttpStatus.OK);
    }

}
