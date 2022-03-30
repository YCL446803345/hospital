
package com.woniu.controller;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.*;
import com.woniu.service.CallService;
import com.woniu.service.HospitalizationBillServer;
import com.woniu.service.PatientService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class HospitalizationController {

    @Autowired
    private HospitalizationBillServer hospitalizationBillServer;

    @Autowired
    private PatientService patientService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CallService callService;

    @GetMapping("createToken")
    public ResponseEntity<String> createToken(){
        String token = UUID.randomUUID().toString();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(token,token);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

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
     * @return 返回值中有五个数 , 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额
     */
    @GetMapping("advancePayment")
    public ResponseEntity<String> advancePayment(Integer id,String status,String token){
        if(redisTemplate.delete(token)){
            Float aFloat = hospitalizationBillServer.updateHospitalizationBill(id,status);
            if(aFloat >= 0){
                return new ResponseEntity<> ("OK", HttpStatus.OK);
            }else{
                return new ResponseEntity<>(String.format("%.2f", aFloat),HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("err",HttpStatus.OK);
        }
    }

    /** 病人现金缴费
     * @param id
     * @param money
     * @return
     */
    @GetMapping("addMoney")
    public ResponseEntity<String> addMoney(Integer id,Float money,String status){
        hospitalizationBillServer.updateMoney(id,money,status);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    /** 按条件查询相关病人的相关数据
     * @param id 根据病人id查找响应的病人
     * @return 返回值中有九个值
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额
     * 第六个是退药集合 第七个是药品集合 第八个是住院天数 第九个是医嘱集合
     */
    @GetMapping("query")
    public ResponseEntity<Cost> query(int id){
        Cost query = hospitalizationBillServer.query(id);
        return new ResponseEntity<> (query, HttpStatus.OK);
    }

    /** 查询所有在院病人所花费费用列表
     * @return 返回值是一个集合,每个集合中有六个数 ,
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额,第六个是病人id
     */
    @GetMapping("queryAllCost")
    public ResponseEntity<PageInfo<Cost>> queryAllCost(String name,String no,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PageInfo<Cost> lists = hospitalizationBillServer.queryAll(name,no,pageNum,pageSize);
        return new ResponseEntity<> (lists, HttpStatus.OK);
    }

    /** 查询所有已出院病人所花费费用列表
     * @return 返回值是一个集合,每个集合中有六个数 ,
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额,第六个是病人id
     */
    @GetMapping("queryAllOut")
    public ResponseEntity<PageInfo<Cost>> queryAllOut(String name,String no,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PageInfo<Cost> pageInfo = hospitalizationBillServer.queryAllOut(name,no,pageNum,pageSize);
        return new ResponseEntity<> (pageInfo, HttpStatus.OK);
    }

    /** 查询所有审核出院病人列表
     * @return
     */
    @GetMapping("leaveHospital")
    public ResponseEntity<PageInfo<Cost>> leaveHospital(String name,String no,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PageInfo<Cost> lists = hospitalizationBillServer.leaveHospital(name,no,pageNum,pageSize);
        return new ResponseEntity<> (lists, HttpStatus.OK);
    }


    /** 查询所有在院病人列表
     * @param patient
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("queryPatients")
    public ResponseEntity<PageInfo<Patient>> queryPatients(Patient patient, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        patient.setStatus("1");
        PageInfo<Patient> pageInfo = patientService.findPatientsByChangeDept(patient, pageNum, pageSize);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

    /** 查询所有出院病人列表
     * @param patient
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("queryPatientsOut")
    public ResponseEntity<PageInfo<Patient>> queryPatientsOut(Patient patient, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        patient.setStatus("2");
        PageInfo<Patient> pageInfo = patientService.findPatientsByChangeDept(patient, pageNum, pageSize);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }


    /** 根据id查询病人的费用记录
     * @param id
     * @return
     */
    @GetMapping("queryPayment")
    public ResponseEntity<PageInfo<PaymentRecord>> queryPayment( @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,Integer id){
        PageInfo<PaymentRecord> paymentRecords = hospitalizationBillServer.queryPayment(pageNum,pageSize,id);
        return new ResponseEntity<>(paymentRecords,HttpStatus.OK);
    }


    /** 查询所有住院病人与出院病人的数量
     * @return
     */
    @GetMapping("countHospitalization")
    public ResponseEntity<List<Integer>> countHospitalization(){
        List<Integer> list = hospitalizationBillServer.countHospitalization();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("countInBill")
    public ResponseEntity<List<Float>> countBill(){
        List<Float> list = hospitalizationBillServer.countInBill();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("countOutBill")
    public ResponseEntity<List<Float>> countOutBill(){
        List<Float> list = hospitalizationBillServer.countOutBill();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /** 这个是单个病人的数据分析 根据病人id去查询病人相关数据
     * @return 分别为住院费用,处方费用,医嘱费用,退药费用
     */
    @GetMapping("queryPersonalData")
    public ResponseEntity<List<Float>> queryPersonalData(Integer id){
        List<Float> list = hospitalizationBillServer.queryPersonalData(id);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /** 这个是单个病人的数据分析,根据病人id去查询病人缴费数据折线图
     * @param id
     * @return
     */
    @GetMapping("queryPersonalTime")
    public ResponseEntity<List<Float>> queryPersonalTime(Integer id){
        List<Float> list = hospitalizationBillServer.queryPersonalTime(id);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /** 查询相关时间内的支付记录   目前还没有时间
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("getAllPayCount")
    public ResponseEntity<List<Float>> getAllPayCount(String startTime,String endTime){
        List<Float> list =  hospitalizationBillServer.getAllPayCount(startTime,endTime);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /** 查询所有住院缴费病人
     * @param patient
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getAllInPatient")
    public ResponseEntity<PageInfo<Patient>> getAllInPatient(Patient patient,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PageInfo<Patient> pageInfo = hospitalizationBillServer.getAllInPatient(pageNum,pageSize,patient);
        return new ResponseEntity<>(pageInfo,HttpStatus.OK);
    }

    @GetMapping("findCall")
    public ResponseEntity<PageInfo<Call>> findCall(Call call,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PageInfo<Call> pageInfo = callService.getCallPatient(pageNum,pageSize,call);
        return new ResponseEntity<>(pageInfo,HttpStatus.OK);
    }

    @GetMapping("updateCallStatus")
    public void updateCallStatus(Integer id){
        callService.updateCallStatus(id);
    }

}

