package com.woniu.controller;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.*;
import com.woniu.service.*;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


//医生站Controller
@RestController
//@CrossOrigin
public class DoctorController {

    @Autowired
    private InHospitalTableService inHospitalTableService;
    @Autowired
    private MedicalAdviceService medicalAdviceService;
    @Autowired
    private ConsultationApplicationService consultationApplicationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private LeaveHospitalService leaveHospitalService;
    @Autowired
    private ProjectService projectService;


    //撤销出院
    @PostMapping("doctor/gotoCancelLeaveHospitalById")
    public void gotoCancelLeaveHospitalById(Integer id){
        leaveHospitalService.gotoCancelLeaveHospitalById(id);
    }

    //申请出院
    @PostMapping("doctor/gotoAddLeaveHospital")
    public ResponseResult<String> gotoAddLeaveHospital(@RequestBody LeaveHospital leaveHospital){
        leaveHospitalService.addLeaveHospital(leaveHospital);
        System.out.println("出院申请成功");
        return new ResponseResult<String>(200,"下达成功");
    }



    //分页查询出院申请列表
    @RequestMapping("doctor/getLeaveHospitalList")
    public ResponseResult<PageInfo<LeaveHospital>> getLeaveHospitalList(@RequestParam(name = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                        @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                        LeaveHospital leaveHospital) {
        ResponseResult<PageInfo<LeaveHospital>> responseResult = new ResponseResult<>();
        try {
            PageInfo<LeaveHospital> leaveHospitalPageInfo = leaveHospitalService.queryLeaveHospital(leaveHospital, pageNum, pageSize);
            responseResult.setData(leaveHospitalPageInfo);
            responseResult.setStatus(200);
            responseResult.setMsg("查询成功...");
        } catch(Exception e) {
            e.printStackTrace();
            responseResult.setStatus(500);
            responseResult.setMsg("查询失败...");
        }
        return responseResult;
    }

    //分页查询患者列表
    @RequestMapping("doctor/getPatientList")
    public ResponseResult<PageInfo<Patient>> getPatientList(@RequestParam(name = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                    @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                            Patient patient) {
        ResponseResult<PageInfo<Patient>> responseResult = new ResponseResult<>();
        try {
            PageInfo<Patient> patientPageInfo = patientService.queryPatient(patient, pageNum, pageSize);
            responseResult.setData(patientPageInfo);
            responseResult.setStatus(200);
            responseResult.setMsg("查询成功...");
        } catch(Exception e) {
            e.printStackTrace();
            responseResult.setStatus(500);
            responseResult.setMsg("查询失败...");
        }
        return responseResult;
    }

    //分页查询预约住院申请列表
    @RequestMapping("doctor/getInHospitalTableList")
    public ResponseResult<PageInfo<InHospitalTable>> getInHospitalTableList(@RequestParam(name = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                            @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                    InHospitalTable inHospitalTable) {
        ResponseResult<PageInfo<InHospitalTable>> responseResult = new ResponseResult<>();
        try {
            PageInfo<InHospitalTable> inHospitalTablePageInfo = inHospitalTableService.queryInHospitalTable(inHospitalTable, pageNum, pageSize);
            responseResult.setData(inHospitalTablePageInfo);
            responseResult.setStatus(200);
            responseResult.setMsg("查询成功...");
        } catch(Exception e) {
            e.printStackTrace();
            responseResult.setStatus(500);
            responseResult.setMsg("查询失败...");
        }
        return responseResult;
    }

    //审核预约住院信息
    @PostMapping("doctor/updateInHospitalTable")
    public void updateInHospitalTable(@RequestBody InHospitalTable inHospitalTable) {
        inHospitalTableService.updateInHospitalTable(inHospitalTable);
    }





    //医嘱申请
    @PostMapping("doctor/gotoAddMedicalAdvice")
    public ResponseResult<String> gotoAddMedicalAdvice(@RequestBody MedicalAdvice medicalAdvice){
        medicalAdviceService.addMedicalAdvice(medicalAdvice);
        System.out.println("医嘱下达成功");
        return new ResponseResult<String>(200,"下达成功");
    }

    //医嘱修改
    @RequestMapping("doctor/updateMedicalAdvice")
    public ResponseResult<String> updateMedicalAdvice(MedicalAdvice medicalAdvice){
        medicalAdviceService.updateMedicalAdvice(medicalAdvice);
        return new ResponseResult<String>(200,"修改成功");
    }

    //医嘱删除
    @RequestMapping("doctor/deleteMedicalAdviceById")
    public ResponseResult<String> deleteMedicalAdvice(Integer id){
        medicalAdviceService.deleteMedicalAdviceById(id);
        return new ResponseResult<String>(200,"删除成功");
    }

    //根据ID查询医嘱
    @RequestMapping("doctor/getMedicalAdviceById")
    public ResponseEntity<MedicalAdvice> getMedicalAdviceById(Integer id){
        MedicalAdvice medicalAdvice = medicalAdviceService.queryMedicalAdviceById(id);
        return new ResponseEntity<MedicalAdvice>(medicalAdvice,HttpStatus.OK);
    }

    //根据ID查询医嘱(关联查询详细)
    @RequestMapping("doctor/getMedicalAdviceById2")
    public ResponseEntity<MedicalAdvice> getMedicalAdviceById2(Integer id){
        MedicalAdvice medicalAdvice = medicalAdviceService.queryMedicalAdviceById2(id);
        return new ResponseEntity<MedicalAdvice>(medicalAdvice,HttpStatus.OK);
    }

    //分页查询医嘱列表
    @RequestMapping("doctor/getMedicalAdviceList")
    public ResponseResult<PageInfo<MedicalAdvice>> getMedicalAdviceList(@RequestParam(name = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                          @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                        MedicalAdvice medicalAdvice) {
        ResponseResult<PageInfo<MedicalAdvice>> responseResult = new ResponseResult<>();
        try {
            PageInfo<MedicalAdvice> medicalAdvicePageInfo = medicalAdviceService.queryMedicalAdvice(medicalAdvice, pageNum, pageSize);
            responseResult.setData(medicalAdvicePageInfo);
            responseResult.setStatus(200);
            responseResult.setMsg("查询成功...");
        } catch(Exception e) {
            e.printStackTrace();
            responseResult.setStatus(500);
            responseResult.setMsg("查询失败...");
        }
        return responseResult;
    }

    //分页查询会诊列表
    @RequestMapping("doctor/getConsultationApplication")
    public ResponseResult<PageInfo<ConsultationApplication>> getConsultationApplication(@RequestParam(name = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                                  @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                                        ConsultationApplication consultationApplication) {
        ResponseResult<PageInfo<ConsultationApplication>> responseResult = new ResponseResult<>();
        try {
            PageInfo<ConsultationApplication> consultationApplicationPageInfo = consultationApplicationService.
                    queryConsultationApplication(consultationApplication, pageNum, pageSize);
            responseResult.setData(consultationApplicationPageInfo);
            responseResult.setStatus(200);
            responseResult.setMsg("查询成功...");
        } catch(Exception e) {
            e.printStackTrace();
            responseResult.setStatus(500);
            responseResult.setMsg("查询失败...");
        }
        return responseResult;
    }

    //编辑会诊信息
    @PostMapping("doctor/updateConsultationApplication")
    public void updateConsultationApplication(@RequestBody ConsultationApplication consultationApplication) {
        consultationApplicationService.updateConsultationApplication(consultationApplication);
    }

    //取消会诊
    @PostMapping("doctor/gotoCancelConsultationApplicationById")
    public void gotoCancelConsultationApplicationById(Integer id){
        consultationApplicationService.gotoCancelConsultationApplicationById(id);
    }

    //获取所有项目列表
    @GetMapping("doctor/findProjectList")
    public ResponseEntity<List<Project>> findProjectList() {
        List<Project> projectList = projectService.findProjectList();
        return new ResponseEntity<List<Project>>(projectList, HttpStatus.OK);
    }

    //编辑医嘱,修改项目
    @PostMapping("doctor/updateMedicalAdviceProject")
    public void updateMedicalAdviceProject(@RequestBody MedicalAdvice medicalAdvice) {
        medicalAdviceService.updateMedicalAdviceProject(medicalAdvice);
    }

    //停止医嘱
    @PostMapping("doctor/gotoStopMedicalAdviceById")
    public void gotoStopMedicalAdviceById(Integer id){
        medicalAdviceService.gotoStopMedicalAdviceById(id);
    }


}
