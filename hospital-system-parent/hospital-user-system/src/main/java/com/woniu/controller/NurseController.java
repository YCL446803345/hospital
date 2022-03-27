
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

/**
 * 护士站模块
 */
@RestController
public class NurseController {
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
    public ResponseEntity<PageInfo<Bed>> findBeds(Bed bed, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<Bed> pageInfo = bedService.findBeds(bed, pageNum, pageSize);
        return new ResponseEntity<PageInfo<Bed>>(pageInfo, HttpStatus.OK);
    }


    /**
     * 获得所有床位列表,在病人预约中使用
     * @return
     */
    @GetMapping("/findBedList")
    public ResponseResult<List<Bed>> findBedList() {
        List<Bed> bedList = bedService.findBedList();
        return new ResponseResult<List<Bed>>(bedList,"OK",200);
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

}
