
package com.woniu.service.impl;

import com.woniu.entity.*;
import com.woniu.mapper.*;
import com.woniu.service.HospitalizationBillServer;
import com.woniu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HospitalizationBillServerImpl implements HospitalizationBillServer {

    private float DAILYFEE = 200.0f;
    //住院账单
    private final HospitalizationBillMapper hospitalizationBillMapper;
    //病人
    private final PatientMapper patientMapper;
    //退药账单
    private final DrugOutBillMapper drugOutBillMapper;
    //处方账单
    private final PrescriptionBillMapper prescriptionBillMapper;
    //医嘱账单
    private final MedicalAdviceBillMapper medicalAdviceBillMapper;
//    //退药
//    private final OutDrugRecordMapper outDrugRecordMapper;
    //处方
    private final DrugMapper drugMapper;
    //医嘱
    private final MedicalAdviceMapper medicalAdviceMapper;

    public HospitalizationBillServerImpl (HospitalizationBillMapper hospitalizationBillMapper, PatientMapper patientMapper, DrugOutBillMapper drugOutBillMapper, PrescriptionBillMapper prescriptionBillMapper, MedicalAdviceBillMapper medicalAdviceBillMapper, OutDrugRecordMapper outDrugRecordMapper, DrugMapper drugMapper, MedicalAdviceMapper medicalAdviceMapper) {
        this.hospitalizationBillMapper = hospitalizationBillMapper;
        this.patientMapper = patientMapper;
        this.drugOutBillMapper = drugOutBillMapper;
        this.prescriptionBillMapper = prescriptionBillMapper;
        this.medicalAdviceBillMapper = medicalAdviceBillMapper;
//        this.outDrugRecordMapper = outDrugRecordMapper;
        this.drugMapper = drugMapper;
        this.medicalAdviceMapper = medicalAdviceMapper;
    }

    /** 生成住院账单
     * @param patientId 病人id
     * @param money 病人余额
     */
    @Override
    public void addHospitalizationBill (Integer patientId, float money) {
        //根据病人id和当前系统时间生成住院账单
        HospitalizationBill hospitalizationBill = new HospitalizationBill();
        hospitalizationBill.setStartTime(new Date());
        hospitalizationBill.setPatientId(patientId);

        //根据病人id修改病人表中的余额
        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setBalance(money);

        patientMapper.updateByPrimaryKeySelective(patient);
        hospitalizationBillMapper.insert(hospitalizationBill);
    }


    /** 提前缴费,主要业务为计算费用
     * @param id 要提前缴费的病人
     * @param money 缴费的钱
     */
    @Override
    public List<Object> updateHospitalizationBill (Integer id, float money) {
        List<Object> query = query(id);
        Float outTotal = (Float)query.get(0);
        Float prescriptionTotal = (Float)query.get(1);
        Float timeTotal = (Float)query.get(2);
        Float medicalAdviceTotal = (Float)query.get(3);
        Float balance = (Float)query.get(4);
        balance = balance - outTotal - prescriptionTotal - timeTotal - medicalAdviceTotal;
        //如果病人缴费,则费用相加
        balance+=money;
        Patient newPatient = new Patient();
        newPatient.setId(id);
        newPatient.setBalance(balance);
        patientMapper.updateByPrimaryKeySelective(newPatient);
        //缴费后将处方表中的状态修改为已缴费状态
        medicalAdviceBillMapper.updateStatus(id);
        //缴费后将医嘱表中的状态修改为已缴费状态
        prescriptionBillMapper.updateStatus(id);
        //缴费后将退药表中的状态修改为已缴费状态
        drugOutBillMapper.updateStatus(id);
        //缴费后将住院天数修改为已缴费天数
        hospitalizationBillMapper.updateDays((int) (timeTotal/DAILYFEE),id);
        //缴费后修改处方缴费时间
        prescriptionBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
        //缴费后修改医嘱缴费时间
        medicalAdviceBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
        //缴费后修改退药时间为当前时间
        drugOutBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
        return getFloats(outTotal, prescriptionTotal, timeTotal, medicalAdviceTotal, balance);
//        //退药集合存入返回值
//        floats.add(query.get(5));
//        //处方集合存入返回值
//        floats.add(query.get(6));
//        //住院天数存入返回值
//        floats.add(query.get(7));
//        //医嘱集合存入返回值
//        floats.add(query.get(8));
//        return floats;
    }


    /** 按条件查询相关病人的相关数据
     * @param id 根据病人id查找响应的病人
     * @return 返回值中有九个值
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额
     * 第六个是退药集合 第七个是药品集合 第八个是住院天数 第九个是医嘱集合
     */
    @Override
    public List<Object> query(int id){
        Patient patient = patientMapper.selectByPrimaryKey(id);
        List<Object> patientBill = getPatientBill(patient);
        //获取退药集合
        List<Drug> drugList = drugMapper.selectByPatientId(id);
        patientBill.add(drugList);
        //获取药品集合
        List<Drug> drugs = drugMapper.selectDrugByPatientId(id);
        patientBill.add(drugs);
        //获取住院天数
        Float aFloat = (Float) patientBill.get(2);
        Integer v = (int)(aFloat / DAILYFEE);
        patientBill.add(v);
        //获取医嘱集合
        List<Project> projects = medicalAdviceMapper.selectProjectByPatientId(id);
        patientBill.add(projects);
        return patientBill;
    }

    /** 查询所有在院病人的相关费用
     * @return
     */
    @Override
    public List<List> queryAll(){
        //查询未出院的病人
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusNotLike("9");//规定9是已出院的
        List<Patient> patients = patientMapper.selectByExample(patientExample);

        List<List> total = new ArrayList<>();
        for (Patient patient : patients) {
            List<Object> patientBill = getPatientBill(patient);
            patientBill.add(patient);
            total.add(patientBill);
        }
        return total;
    }

    /** 查询所有已出院病人所花费费用列表
     * @return
     */
    @Override
    public List<List> queryAllOut ( ) {
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusEqualTo("9");//规定9是已出院的
        List<Patient> patients = patientMapper.selectByExample(patientExample);


        List<List> total = new ArrayList<>();
        for (Patient patient : patients) {
            List<Object> patientBill = getPatientBill(patient);
            patientBill.add(patient);
            total.add(patientBill);
        }
        return total;
    }

    /** 查询所有审核出院的病人信息
     * @return 返回有六个值,前五个分别为退药费用,处方费用,出院费用,医嘱费用,余额 第六个值为当前病人
     */
    @Override
    public List<List> leaveHospital () {
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusEqualTo("8");//规定8是审核出院的
        List<Patient> patients = patientMapper.selectByExample(patientExample);

        List<List> total = new ArrayList<>();
        for (Patient patient : patients) {
            List<Object> bills = getPatientBill(patient);
            bills.add(patient);
            total.add(bills);
        }
        return total;
    }


    /** 提取出来的方法,根据病人查询所产生的费用
     * @param patient 所需要查询费用的病人
     * @return
     */
    public List<Object> getPatientBill(Patient patient){
        Integer id = patient.getId();
        //获取病人余额
        Float balance = patient.getBalance();
        //获取退药的费用
        List<Float> floats1 = drugOutBillMapper.selectBillMoneyByPatientId(id);
        float outTotal = 0.0f;
        for (Float aFloat : floats1) {
            outTotal+=aFloat;
        }
        //获取处方的费用
        List<Float> floats2 = prescriptionBillMapper.selectMoneyByPatientId(id);
        float prescriptionTotal = 0.0f;
        for (Float aFloat : floats2) {
            prescriptionTotal+=aFloat;
        }
        //获取住院费用
        int i = timeOperation(patient.getAppointmenttTime(), new Date());
        float timeTotal = i * DAILYFEE;
        //获取医嘱的费用
        List<Float> floats3 = medicalAdviceBillMapper.selectMoneyByPatientId(id);
        float medicalAdviceTotal = 0.0f;
        for (Float aFloat : floats3) {
            medicalAdviceTotal+=aFloat;
        }
        return getFloats(outTotal, prescriptionTotal, timeTotal, medicalAdviceTotal,balance);
    }

    /** 工具方法 将五个费用包装成list
     * @param outTotal 退药费用
     * @param prescriptionTotal  处方费用
     * @param timeTotal  出院费用
     * @param medicalAdviceTotal  医嘱费用
     * @param balance 余额
     * @return
     */
    private List<Object> getFloats (float outTotal, float prescriptionTotal, float timeTotal, float medicalAdviceTotal,Float balance) {
        List<Object> floats = new ArrayList<>();
        floats.add(outTotal);
        floats.add(prescriptionTotal);
        floats.add(timeTotal);
        floats.add(medicalAdviceTotal);
        floats.add(balance);
        return floats;
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

}

