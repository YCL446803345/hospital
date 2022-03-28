package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;
import com.woniu.service.PrescriptionDrugService;
import com.woniu.service.PrescriptionService;
import com.woniu.service.WorkerService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 药房处方控制类
 */
@RestController
public class DrugPreController {

    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private PrescriptionDrugService prescriptionDrugService;
    @Autowired
    private WorkerService workerService;


    //查询处方列表+分页+模糊查询
    @GetMapping("drug/prescription")
    public ResponseResult<PageInfo<Prescription>> findAllPrescription(String doctorName,String nurseName,String preName,
                                                                      @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                                      @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Prescription> preDrugList = prescriptionService.getPresDrugByMany(doctorName,nurseName,preName);
        PageInfo preDrugPageInfo = new PageInfo(preDrugList);
        List<Prescription> prescriptionList2 = preDrugPageInfo.getList();
        //相同处方排除法,分页数据会有误,待修改
        //计数器
        int count = 0;
        for (int i = 0; i < prescriptionList2.size(); i++) {
            for (int j = 0; j < prescriptionList2.size(); j++) {
                if(i != j && prescriptionList2.get(i).getId()== prescriptionList2.get(j).getId()) {
                    prescriptionList2.remove(prescriptionList2.get(j));
                    count++;
                }
            }
        }
        preDrugPageInfo.setTotal(preDrugPageInfo.getTotal()-count);
        preDrugPageInfo.setList(prescriptionList2);
        return new ResponseResult<PageInfo<Prescription>>(preDrugPageInfo,"OK",200);
    }

    //通过处方id查找该处方下所有药品及数量
    @GetMapping("drug/getDrugByPreId")
    public ResponseResult<List<PrescriptionDrug>> findDrugNameAndNum(Integer pid){
        List<PrescriptionDrug> prescriptionDrugList =  prescriptionDrugService.getDrugNameAndNumByPreId(pid);
        return new ResponseResult<List<PrescriptionDrug>>(prescriptionDrugList,"OK",200);
    }

    //药房撤销处方同时记录撤销人和当前撤销时间
    @PostMapping("drug/updatePreStatus")
    public ResponseResult updatePreDrugStatus(@RequestParam("pid") Integer pid,@RequestParam("account") String account){
        prescriptionService.updateStatusById(pid,account);
        return ResponseResult.ok();
    }

    //通过处方id配药,支持批量配药
    //同时添加发药记录
    //添加发药账单记录
    @PostMapping("drug/sendDrug")
    public ResponseResult<LinkedHashSet<String>> updatePreDrugStatusAndAddSendDrug(@RequestParam("idStrs") String idStrs,@RequestParam("account") String account){
        List<String> list = Arrays.asList(idStrs.split(","));
        List<String> newlist = new ArrayList<String>(list);
        LinkedHashSet<String> drugInfo = prescriptionService.updateByBatch(newlist,account);
        return new ResponseResult<LinkedHashSet<String>>(drugInfo,"OK",200);
    }

    //通过处方id查询该处方开单医生和审核护士和创建时刻
    @GetMapping("drug/getDrugDNTBypreId")
    public ResponseResult<Prescription> findDrugNameAndNumAndCreateTime(Integer pid){
        Prescription drugNameAndNumAndCreateTime = prescriptionService.getDrugNameAndNumAndCreateTime(pid);
        return new ResponseResult<Prescription>(drugNameAndNumAndCreateTime,"OK",200);

    }

    //药房查找所有医生
    @GetMapping("drug/findAllDoctor")
    public ResponseResult<List<String>> findAllDoctor(){
        List<String> doctorList = workerService.findAllDoctor();
        return new ResponseResult<List<String>>(doctorList,"OK",200);

    }

}
