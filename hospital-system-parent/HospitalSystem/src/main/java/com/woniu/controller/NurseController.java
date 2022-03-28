
package com.woniu.controller;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.*;
import com.woniu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 护士站模块
 */
@RestController
public class
NurseController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private PrescriptionBillService prescriptionBillService;

    @Autowired
    private MedicalAdviceBillService medicalAdviceBillService;

    @Autowired
    private DrugOutBillService drugOutBillService;

    @Autowired
    private NursingRecordService nursingRecordService;

    @Autowired
    private MedicalAdviceService medicalAdviceService;


    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DrugService drugService;

    /**
     * 职工登录时得到职工的所有信息
     */
    @GetMapping("/getWorker")
    @ResponseBody
    public ResponseEntity<Worker> getWorker(String account) {
        Worker worker=workerService.getWorker(account);
        return new ResponseEntity<Worker>(worker, HttpStatus.OK);
    }



    /**
     * 查询非待床病人信息，安排并修改病人床位
     */
    @GetMapping("/findPatients")
    @ResponseBody
    public ResponseEntity<PageInfo<Patient>> findPatients(Patient patient, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<Patient> pageInfo = patientService.findPatients(patient, pageNum, pageSize);
        return new ResponseEntity<PageInfo<Patient>>(pageInfo, HttpStatus.OK);
    }


    /**
     * 查询所有病人信息，方便护士长跟换病人科室
     */
    @GetMapping("/findPatientsByChangeDept")
    @ResponseBody
    public ResponseEntity<PageInfo<Patient>> findPatientsByChangeDept(Patient patient, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<Patient> pageInfo = patientService.findPatientsByChangeDept(patient, pageNum, pageSize);
        return new ResponseEntity<PageInfo<Patient>>(pageInfo, HttpStatus.OK);
    }

    /**
     * 通过病人id查询病人护理记录
     */
    @GetMapping("/findNursingRecordsByPatientId")
    @ResponseBody
    public ResponseEntity<PageInfo<NursingRecord>> findNursingRecordsByPatientId(NursingRecord nursingRecord, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<NursingRecord> pageInfo = nursingRecordService.findNursingRecordsByPatientId(nursingRecord, pageNum, pageSize);
        return new ResponseEntity<PageInfo<NursingRecord>>(pageInfo, HttpStatus.OK);
    }

    /**
     * 在护士站模块中更换病人科室，此时修改科室id，并将床位id，医生护士全部设置成null
     * @return
     */
    @PostMapping("/updatePatientDept")
    public void updatePatientDept(@RequestBody Patient patient) {
        patientService.updatePatientDept(patient);
    }



    /**
     * 在入院管理中用来修改病人信息
     * @return
     */
    @PostMapping("/updatePatient")
    public void updatePatient(@RequestBody Patient patient) {
        patientService.updatePatient(patient);
    }

    /**
     * 查询待床病人信息，安排并修改病人床位
     */
    @GetMapping("/findPatientsWithNotBed")
    @ResponseBody
    public ResponseEntity<PageInfo<Patient>> findPatientsWithNotBed(Patient patient, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<Patient> pageInfo = patientService.findPatientsWithNotBed(patient, pageNum, pageSize);
        return new ResponseEntity<PageInfo<Patient>>(pageInfo, HttpStatus.OK);
    }


    /**
     * 获得所有科室列表
     * @return
     */
    @GetMapping("/findDepts")
    public ResponseEntity<List<Dept>> findDepts() {
        List<Dept> depts = deptService.findDeptList();
        return new ResponseEntity<List<Dept>>(depts, HttpStatus.OK);
    }


    /**
     * 获得所有床位列表,在床位管理中使用
     * @return
     */
    @GetMapping("/findBeds")
    public ResponseEntity<List<Bed>> findBeds(Bed bed) {
        List<Bed> beds = bedService.findBeds(bed);
        return new ResponseEntity<List<Bed>>(beds, HttpStatus.OK);
    }

    /**
     * 终止床位，在床位管理中使用
     * @return
     */
    @PostMapping("/stopUseBed")
    public void stopUseBed(@RequestBody Patient patient) {
        patientService.stopUseBed(patient);
    }

    /**
     * 根据指定科室搜索床位,在修改床位中使用
     * @return
     */
    @GetMapping("/findBedsByDeptId")
    public ResponseEntity<List<Bed>> findBedsByDeptId(Integer deptId) {
        List<Bed> beds = bedService.findBedsByDeptId(deptId);
        return new ResponseEntity<List<Bed>>(beds, HttpStatus.OK);
    }

    /**
     * 修改病人床位,同时将床位状态改为正在使用
     * @return
     */
    @PostMapping("/updateBed")
    public void updateBed(@RequestBody Patient patient) {
        patientService.updateBed(patient);
    }

    /**
     * 修改病人床位,同时将床位状态改为正在使用
     * @return
     */
    @PostMapping("/updateBedByPatientWithOutBed")
    public void updateBedByPatientWithOutBed(@RequestBody Patient patient) {
        patientService.updateBedByPatientWithOutBed(patient);
    }

    /**
     * 修改病人床位，同时将床位状态改为未被使用
     * @return
     */
    @PostMapping("/changeBedStatusOff")
    public void changeBedStatusOff(@RequestBody Patient patient) {
        bedService.changeBedStatusOff(patient);
    }

    /**
     * 获得所有医生列表
     * @return
     */
    @GetMapping("/findDoctorsByDepeId")
    public ResponseEntity<List<Worker>> findDoctorsByDepeId(Integer deptId) {
        List<Worker> workers = workerService.findDoctorsByDepeId(deptId);
        return new ResponseEntity<List<Worker>>(workers, HttpStatus.OK);
    }

    /**
     * 获得所有护士列表
     * @return
     */
    @GetMapping("/findNursesByDepeId")
    public ResponseEntity<List<Worker>> findNursesByDepeId(Integer deptId) {
        List<Worker> workers = workerService.findNursesByDepeId(deptId);
        return new ResponseEntity<List<Worker>>(workers, HttpStatus.OK);
    }

    /**
     * 获得所有护士列表
     * @return
     */
    @PostMapping("/updateDoctorOrNurse")
    public void updateDoctorOrNurse(@RequestBody Patient patient) {
        patientService.updateDoctorOrNurse(patient);
    }

    /**
     * 查询病人住院费用
     * @return
     */
    @PostMapping("/getHospitalizationBill")
    public ResponseEntity<HospitalizationBill> getHospitalizationBill(@RequestBody Patient patient) {
        HospitalizationBill hospitalizationBill = patientService.getHospitalizationBill(patient);
        return new ResponseEntity<HospitalizationBill>(hospitalizationBill,HttpStatus.OK);
    }

    /**
     * 查询病人处方账单
     * @return
     */
    @PostMapping("/getPrescriptionBill")
    public ResponseEntity<List<Drug>> getPrescriptionBill(@RequestBody Patient patient) {
        final List<Drug> drugs = prescriptionBillService.getPrescriptionBill(patient);
        return new ResponseEntity<List<Drug>>(drugs,HttpStatus.OK);
    }

    /**
     * 查询病人医嘱账单
     * @return
     */
    @PostMapping("/getMedicalAdviceBill")
    public ResponseEntity<List<Project>> getMedicalAdviceBill(@RequestBody Patient patient) {
        final List<Project> projects = medicalAdviceBillService.getMedicalAdviceBill(patient);
        return new ResponseEntity<List<Project>>(projects,HttpStatus.OK);
    }

    /**
     * 查询病人退药账单
     * @return
     */
    @PostMapping("/getDrugOutBill")
    public ResponseEntity<List<Drug>> getDrugOutBill(@RequestBody Patient patient) {
        final List<Drug> drugs = drugOutBillService.getDrugOutBill(patient);
        return new ResponseEntity<List<Drug>>(drugs,HttpStatus.OK);
    }

    /**
     * 添加护理记录
     * @return
     */
    @PostMapping("/addNursingRecord")
    public void addNursingRecord(@RequestBody NursingRecord nursingRecord) {
        nursingRecordService.addNursingRecord(nursingRecord);
    }

    /**
     * 删除一条护理记录
     * @return
     */
    @GetMapping("/deleteNursingRecord")
    public void deleteNursingRecord(Integer id) {
       nursingRecordService.deleteNursingRecord(id);
    }

    /**
     * 添加护理记录
     * @return
     */
    @PostMapping("/updateNursingRecord")
    public void updateNursingRecord(@RequestBody NursingRecord nursingRecord) {
        nursingRecord.setModifyTime(new Date());
        nursingRecordService.updateNursingRecord(nursingRecord);
    }

    /**
     * 查询病人医嘱信息
     * @return
     */
    @GetMapping("/getMedicalAdvices")
    public ResponseEntity<PageInfo<MedicalAdvice>> getMedicalAdvices(MedicalAdvice medicalAdvice,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<MedicalAdvice> medicalAdvices = medicalAdviceService.getMedicalAdvices(medicalAdvice,pageNum,pageSize);
        return new ResponseEntity<PageInfo<MedicalAdvice>>(medicalAdvices,HttpStatus.OK);
    }

    /**
     * 查询病人医嘱信息
     * @return
     */
    @GetMapping("/checkMedicalAdvice")
    public void checkMedicalAdvice(Integer id) {
        medicalAdviceService.checkMedicalAdvice(id);
    }

    /**
     * 撤销医嘱
     * @return
     */
    @GetMapping("/stopMedicalAdvice")
    public void stopMedicalAdvice(Integer id) {
        medicalAdviceService.stopMedicalAdvice(id);
    }

    /**
     * 执行医嘱
     * @return
     */
    @GetMapping("/doMedicalAdvice")
    public void doMedicalAdvice(Integer id) {
        medicalAdviceService.doMedicalAdvice(id);
    }


    /**
     * 查询病人处方信息
     * @return
     */
    @GetMapping("/getPrescriptions")
    public ResponseEntity<PageInfo<Prescription>> getPrescriptions(Prescription prescription ,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<Prescription> prescriptions = prescriptionService.getPrescriptions(prescription,pageNum,pageSize);
        return new ResponseEntity<PageInfo<Prescription>>(prescriptions,HttpStatus.OK);
    }

    /**
     * 通过处方id查询药品星系
     * @return
     */
    @GetMapping("/getDrugsByPrescriptionId")
    public ResponseEntity<List<Drug>> getDrugsByPrescriptionId(Integer id) {
        final List<Drug> drugs =drugService.getDrugsByPrescriptionId(id);
        return new ResponseEntity<List<Drug>>(drugs,HttpStatus.OK);
    }

    /**
     * 校对处方
     * @return
     */
    @GetMapping("/checkPrescription")
    public void checkPrescription(Integer id) {
        prescriptionService.checkPrescription(id);
    }

    /**
     * 执行处方
     * @return
     */
    @GetMapping("/doPrescription")
    public void doPrescription(Integer id) {
        prescriptionService.doPrescription(id);
    }

    /**
     * 撤销处方
     * @return
     */
    @GetMapping("/stopPrescription")
    public void stopPrescription(Integer id) {
        prescriptionService.stopPrescription(id);
    }

    /**
     * 审核出院
     * @return
     */
    @GetMapping("/doOutHospital")
    public void doOutHospital(Integer id) {
        patientService.doOutHospital(id);
    }


    /**
     * 添加床位
     * @return
     */
    @GetMapping("/addBed")
    public void addBed(Integer deptId) {
        bedService.addBed(deptId);
    }

}
