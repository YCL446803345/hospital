package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.*;
import com.woniu.mapper.*;
import com.woniu.service.MedicalAdviceService;
import com.woniu.service.PatientService;
import com.woniu.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.util.Length;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("patientService")
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private BedMapper bedMapper;

    @Autowired
    private HospitalizationBillMapper hospitalizationBillMapper;

    @Autowired
    private MedicalAdviceMapper medicalAdviceMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Override
    public PageInfo<Patient> findPatients(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Patient> patientList = patientMapper.findPatients(patient);
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

    /**
     * 修改非待床病人的床位
     * @param patient
     */
    @Override
    public void updateBed(Patient patient) {
        patientMapper.updateBed(patient);
        bedMapper.changeBedStatusOn(patient);
    }

    @Override
    public void updateDoctorOrNurse(Patient patient) {
        patientMapper.updateDoctorOrNurse(patient);
    }



    @Override
    public void updateBalance (Float o,Integer id,String status) {
        Patient patient = patientMapper.selectByPrimaryKey(id);
        if(patient.getBalance()!=null){
            float v = patient.getBalance() + o;
            patient.setBalance(v);
        }else{
            patient.setBalance(o);
        }
        if(status!=null){
            patient.setStatus(status);
            patient.setNurseId(9);
            patient.setDeptId(9);
        }
        patient.setStartDate(new Date());
        patientMapper.updateByPrimaryKeySelective(patient);

    }
    @Override
    public PageInfo<Patient> findPatientsWithNotBed(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Patient> patientList = patientMapper.findPatientsWithNotBed(patient);
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

    /**
     * 给待床病人安排床位，同时修改床位状态
     * @param patient
     */
    @Override
    public void updateBedByPatientWithOutBed(Patient patient) {
        patientMapper.updateBedByPatientWithOutBed(patient);
        bedMapper.changeBedStatusOn(patient);
    }

    /**
     * 终止使用床位
     * @param patient
     */
    @Override
    public void stopUseBed(Patient patient) {
        patientMapper.stopUseBed(patient);
        bedMapper.changeBedStatusOff(patient);

    }


    //分页模糊查询患者列表
    public PageInfo<Patient> queryPatient(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Patient> patientList = patientMapper.queryPatientList(patient);
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

    /**
     * 护士站模块
     *查询所有病人信息，方便护士长跟换病人科室
     * @param patient
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Patient> findPatientsByChangeDept(Patient patient, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Patient> patientList = patientMapper.findPatientsByChangeDept(patient);
        if(patientList.size()>0){
            for (int i = 0; i < patientList.size(); i++) {
                Integer medicalAdvicesNeedForCheckNum=medicalAdviceMapper.getMedicalAdvicesNeedForCheckNum(patientList.get(i).getId());
                Integer prescriptionNeedForCheckNum=prescriptionMapper.getPrescriptionNeedForCheckNum(patientList.get(i).getId());
                patientList.get(i).setMedicalAdviceNeedForCheck(medicalAdvicesNeedForCheckNum);
                patientList.get(i).setPrescriptionNeedForCheck(prescriptionNeedForCheckNum);
            }
        }
        PageInfo<Patient> patientPageInfo = new PageInfo<>(patientList);
        return patientPageInfo;
    }

    /**
     * 在护士站模块中更换病人科室，此时修改科室id，并将床位id，医生护士全部设置成null
     * @return
     */
    @Override
    public void updatePatientDept(Patient patient) {
        patientMapper.stopUseBed(patient);
        bedMapper.changeBedStatusOff(patient);
    }

    /**
     * 在入院管理中心修改用户信息
     * @param patient
     */
    @Override
    public void updatePatient(Patient patient) {
        patientMapper.updatePatient(patient);

    }

    /**
     * 查询病人住院时长并计算住院金额
     * @param patient
     */
    @Override
    public HospitalizationBill getHospitalizationBill(Patient patient) {
        HospitalizationBillExample hospitalizationBillExample = new HospitalizationBillExample();
        HospitalizationBillExample.Criteria criteria = hospitalizationBillExample.createCriteria();
        criteria.andPatientIdEqualTo(patient.getId());
        List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectByExample(hospitalizationBillExample);
        HospitalizationBill hospitalizationBill = hospitalizationBills.get(0);

        int day=0;
        if(hospitalizationBill.getEndTime()==null||hospitalizationBill.getEndTime().equals("")){
            day=timeOperation(hospitalizationBill.getStartTime(),new Date());
        }else {
            day=timeOperation(hospitalizationBill.getStartTime(),hospitalizationBill.getEndTime());
        }
        double sumMoney=(double)day*200;

        hospitalizationBill.setNeedPayDays(day);

        hospitalizationBill.setSumMoney(sumMoney);


        return hospitalizationBill;

    }


    /** 工具方法 运算两个时间之间差多少天,
     * @param date1 起始时间
     * @param date2 终止时间
     * @return 两个日期的差值,不满一天算一天
     */
    public int timeOperation(Date date1, Date date2){
        //计算两个时间间隔的毫秒值
        double time1 = date2.getTime() - date1.getTime();
        //计算天数,并强转成int类型
        return (int) Math.ceil(time1 / 1000 / 60 / 60 / 24);
    }

    /**
     * 冰热出院审核
     * @param id
     */
    public void doOutHospital(Integer id) {
        patientMapper.doOutHospital(id);
    }

    @Override
    public void updatePatientByDoctor(Patient patient) {
        patientMapper.updatePatientByDoctor(patient);
    }
}

