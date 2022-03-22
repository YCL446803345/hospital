package com.woniu.controller;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Bed;
import com.woniu.entity.Dept;
import com.woniu.entity.Patient;
import com.woniu.entity.Worker;
import com.woniu.service.BedService;
import com.woniu.service.DeptService;
import com.woniu.service.PatientService;
import com.woniu.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.soap.Addressing;
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

    /**
     * 查询病人信息，安排并修改病人床位
     */
    @GetMapping("/findPatients")
    @ResponseBody
    public ResponseEntity<PageInfo<Patient>> findPatients(Patient patient, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<Patient> pageInfo = patientService.findPatients(patient, pageNum, pageSize);
        return new ResponseEntity<PageInfo<Patient>>(pageInfo, HttpStatus.OK);
    }


    /**
     * 获得所有科室列表
     * @return
     */
    @GetMapping("/findDepts")
    public ResponseEntity<List<Dept>> findDepts() {
        List<Dept> depts = deptService.findDepts();
        return new ResponseEntity<List<Dept>>(depts, HttpStatus.OK);
    }

    /**
     * 根据指定科室搜索床位
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
    @GetMapping("/findDoctors")
    public ResponseEntity<List<Worker>> findDoctors() {
        List<Worker> workers = workerService.findDoctors();
        return new ResponseEntity<List<Worker>>(workers, HttpStatus.OK);
    }

    /**
     * 获得所有护士列表
     * @return
     */
    @GetMapping("/findNurses")
    public ResponseEntity<List<Worker>> findNurses() {
        List<Worker> workers = workerService.findNurses();
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

}
