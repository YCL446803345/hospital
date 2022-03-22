//package com.woniu.service.impl;
//
//import com.woniu.entity.HospitalizationBill;
//import com.woniu.entity.Patient;
//import com.woniu.mapper.*;
//import com.woniu.service.HospitalizationBillServer;
//import com.woniu.util.TimeUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//@Transactional
//public class HospitalizationBillServerImpl implements HospitalizationBillServer {
//
//    private float DAILYFEE = 200.0f;
//
//    @Autowired
//    private HospitalizationBillMapper hospitalizationBillMapper;
//    @Autowired
//    private PatientMapper patientMapper;
//    @Autowired
//    private DrugOutBillMapper drugOutBillMapper;
//    @Autowired
//    private PrescriptionBillMapper prescriptionBillMapper;
//    @Autowired
//    private MedicalAdviceBillMapper medicalAdviceBillMapper;
//
//    /** 生成住院账单
//     * @param patientId 病人id
//     * @param money 病人余额
//     */
//    @Override
//    public void addHospitalizationBill (Integer patientId, float money) {
//        //根据病人id和当前系统时间生成住院账单
//        HospitalizationBill hospitalizationBill = new HospitalizationBill();
//        hospitalizationBill.setStartTime(new Date());
//        hospitalizationBill.setPatientId(patientId);
//
//        //根据病人id修改病人表中的余额
//        Patient patient = new Patient();
//        patient.setId(patientId);
//        patient.setBalance(money);
//
//        patientMapper.updateByPrimaryKeySelective(patient);
//        hospitalizationBillMapper.insert(hospitalizationBill);
//    }
//
//
//    /** 提前缴费
//     * @param id 要提前缴费的病人
//     * @param money 缴费的钱
//     */
//    @Override
//    public void updateHospitalizationBill (Integer id, float money) {
//        Patient patient = patientMapper.selectByPrimaryKey(id);
//        //获取病人余额
//        Float balance = patient.getBalance();
//        //获取退药的费用
//        List<Float> floats1 = drugOutBillMapper.selectBillMoneyByPatientId(id);
//        float outTotal = 0.0f;
//        for (Float aFloat : floats1) {
//            outTotal+=aFloat;
//        }
//        //获取处方的费用
//        List<Float> floats2 = prescriptionBillMapper.selectMoneyByPatientId(id);
//        float prescriptionTotal = 0.0f;
//        for (Float aFloat : floats2) {
//            prescriptionTotal+=aFloat;
//        }
//        //获取住院费用
//        int i = timeOperation(patient.getAppointmenttTime(), new Date());
//        float timeTotal = i * DAILYFEE;
//        //获取医嘱的费用
//        List<Float> floats3 = medicalAdviceBillMapper.selectMoneyByPatientId(id);
//        float medicalAdviceTotal = 0.0f;
//        for (Float aFloat : floats3) {
//            medicalAdviceTotal+=aFloat;
//        }
//        balance = balance - outTotal - prescriptionTotal - timeTotal - medicalAdviceTotal;
//        //如果病人缴费,则费用相加
//        balance+=money;
//        Patient newPatient = new Patient();
//        newPatient.setId(id);
//        newPatient.setBalance(balance);
//        patientMapper.updateByPrimaryKeySelective(newPatient);
//        //缴费后将处方表中的状态修改为已缴费状态
//        medicalAdviceBillMapper.updateStatus(id);
//        //缴费后将医嘱表中的状态修改为已缴费状态
//        prescriptionBillMapper.updateStatus(id);
//        //缴费后将退药表中的状态修改为已缴费状态
//        drugOutBillMapper.updateStatus(id);
//        //缴费后将住院天数修改为已缴费天数
//        hospitalizationBillMapper.updateDays(i,id);
//        //缴费后修改处方缴费时间
//        prescriptionBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
//        //缴费后修改医嘱缴费时间
//        medicalAdviceBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
//        //缴费后修改退药时间为当前时间
//        drugOutBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
//    }
//
//
//    /** 工具类 运算两个时间之间差多少天,
//     * @param date1 起始时间
//     * @param date2 终止时间
//     * @return 两个日期的差值,不满一天算一天
//     */
//    public int timeOperation(Date date1, Date date2){
//        //计算两个时间间隔的毫秒值
//        double time1 = date2.getTime() - date1.getTime();
//        //计算天数,并强转成int类型
//        return (int) Math.ceil(time1 / 1000 / 60 / 60 / 24);
//    }
//
//}
