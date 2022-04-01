
package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.*;
import com.woniu.mapper.*;
import com.woniu.service.HospitalizationBillServer;
import com.woniu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    private final PaymentRecordMapper paymentRecordMapper;
    //处方
    private final DrugMapper drugMapper;
    //医嘱
    private final MedicalAdviceMapper medicalAdviceMapper;

    @Autowired
    private LeaveHospitalMapper leaveHospitalMapper;

    @Autowired
    private BedMapper bedMapper;

    public HospitalizationBillServerImpl (HospitalizationBillMapper hospitalizationBillMapper, PatientMapper patientMapper, DrugOutBillMapper drugOutBillMapper, PrescriptionBillMapper prescriptionBillMapper, MedicalAdviceBillMapper medicalAdviceBillMapper, PaymentRecordMapper paymentRecordMapper, DrugMapper drugMapper, MedicalAdviceMapper medicalAdviceMapper) {
        this.hospitalizationBillMapper = hospitalizationBillMapper;
        this.patientMapper = patientMapper;
        this.drugOutBillMapper = drugOutBillMapper;
        this.prescriptionBillMapper = prescriptionBillMapper;
        this.medicalAdviceBillMapper = medicalAdviceBillMapper;
        this.paymentRecordMapper = paymentRecordMapper;
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
     */
    @Override
    public Float updateHospitalizationBill (Integer id,String status) {
        Cost query = query(id);
        Float outTotal = query.getDrugOut();
        Float prescriptionTotal = query.getPrescription();
        Float timeTotal = query.getHospitalization();
        Float medicalAdviceTotal = query.getMedicalAdvice();
        Float balance = query.getBalance();
        balance = balance - outTotal - prescriptionTotal - timeTotal - medicalAdviceTotal;
        if(balance < 0){
            return balance;
        }
        Patient newPatient = new Patient();
        newPatient.setId(id);
        newPatient.setBalance(balance);
        if(status!=null){
            newPatient.setEndTime(new Date());
            newPatient.setStatus("3");
            newPatient.setBedId(null);
            newPatient.setDoctorId(9);
            newPatient.setNurseId(9);
            Bed bed = bedMapper.selectBedByPatientId(id);
            if(bed!=null) {
                Patient bedPatient = new Patient();
                bedPatient.setBedId(bed.getId());
                bedMapper.changeBedStatusOff(bedPatient);
            }
            LeaveHospital leaveHospital = new LeaveHospital();
            leaveHospital.setPatientId(id);
            leaveHospital.setLeaveStatus(3);
            leaveHospitalMapper.updateByPrimaryKeySelective(leaveHospital);
            HospitalizationBill hospitalizationBill = new HospitalizationBill();
            hospitalizationBill.setId(hospitalizationBillMapper.selectIdByPatientId(id));
            hospitalizationBill.setEndTime(new Date());
            hospitalizationBillMapper.updateByPrimaryKeySelective(hospitalizationBill);
        }
//        newPatient.setBalance();
        query.setPatient(newPatient);
        //修改病人余额
        patientMapper.updateByPrimaryKeySelective(newPatient);

        if(outTotal!=0){
            //缴费后将退药表中的状态修改为已缴费状态
            drugOutBillMapper.updateStatus(id);
            //缴费后修改退药时间为当前时间
            drugOutBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);

            //向缴费记录表中添加一条缴费数据
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setPatientId(id);
            paymentRecord.setType("4");
            paymentRecord.setUpdatemoney(query.getDrugOut());
            paymentRecord.setTime(new Date());
            paymentRecordMapper.insert(paymentRecord);
        }


       if(prescriptionTotal!=0){
           //缴费后将处方表中的状态修改为已缴费状态
           prescriptionBillMapper.updateStatus(id);
           //缴费后修改处方缴费时间
           prescriptionBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);

           //向缴费记录表中添加一条缴费数据
           PaymentRecord paymentRecord = new PaymentRecord();
           paymentRecord.setPatientId(id);
           paymentRecord.setType("2");
           paymentRecord.setUpdatemoney(query.getPrescription());
           paymentRecord.setTime(new Date());
           paymentRecordMapper.insert(paymentRecord);
       }

        if(medicalAdviceTotal!=0){
            //缴费后将医嘱表中的状态修改为已缴费状态
            medicalAdviceBillMapper.updateStatus(id);
            //缴费后修改医嘱缴费时间
            medicalAdviceBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);

            //向缴费记录表中添加一条缴费数据
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setPatientId(id);
            paymentRecord.setType("3");
            paymentRecord.setUpdatemoney(query.getMedicalAdvice());
            paymentRecord.setTime(new Date());
            paymentRecordMapper.insert(paymentRecord);
        }
        int i = (int) (timeTotal/DAILYFEE);
        if(i!=0){
            //缴费后将住院天数修改为已缴费天数  此时应该是将住院天数进行叠加
            HospitalizationBillExample hospitalizationBillExample = new HospitalizationBillExample();
            HospitalizationBillExample.Criteria criteria = hospitalizationBillExample.createCriteria();
            criteria.andPatientIdEqualTo(id);
            List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectByExample(hospitalizationBillExample);
            HospitalizationBill hospitalizationBill = hospitalizationBills.get(0);
            if(hospitalizationBill.getPayDays()!=null){
                hospitalizationBillMapper.updateDays(hospitalizationBill.getPayDays()+i,id);
            }else {
                hospitalizationBillMapper.updateDays(i,id);
            }
            //缴费后修改缴费时间
            hospitalizationBillMapper.updateDate(TimeUtil.getNowTime(new Date()),id);
            if(status!=null){
                hospitalizationBillMapper.updateEndTime(new Date(),id);
            }
            //向缴费记录表中添加一条缴费数据
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setPatientId(id);
            paymentRecord.setType("5");
            paymentRecord.setUpdatemoney(query.getDays()*DAILYFEE);
            paymentRecord.setTime(new Date());
            paymentRecordMapper.insert(paymentRecord);
        }
        return balance;
    }


    /** 按条件查询相关病人的相关数据
     * @param id 根据病人id查找响应的病人
     * @return 返回值中有九个值
     * 第一个是退药费用 第二个是处方费用 第三个是住院费用 第四个是医嘱费用 第五个是余额
     * 第六个是退药集合 第七个是药品集合 第八个是住院天数 第九个是医嘱集合
     */
    @Override
    public Cost query(int id){
        Patient patient = patientMapper.selectByPrimaryKey(id);
        Cost patientBill = getPatientBill(patient);
        patientBill.setPatient(patient);
        //获取退药集合
        List<Drug> drugList = drugMapper.selectByPatientId(id);
        patientBill.setDrugOutList(drugList);
        //获取药品集合
        List<Drug> drugs = drugMapper.selectDrugByPatientId(id);
        patientBill.setDrugInList(drugs);
        //获取住院天数
        patientBill.setDays((int)(patientBill.getHospitalization() / DAILYFEE));
        //获取医嘱集合
        List<Project> projects = medicalAdviceMapper.selectProjectByPatientId(id);
        patientBill.setProjectList(projects);
        return patientBill;
    }

    /** 查询所有在院病人的相关费用
     * @return
     */
    @Override
    public PageInfo<Cost> queryAll(String name,String no,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        //查询未出院的病人
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusNotLike("3");//规定3是已出院的
        criteria.andStatusNotLike("9");//规定3是已出院的
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");//模糊查询
        }
        if(!StringUtils.isEmpty(no)){
            criteria.andNoLike("%"+no+"%");
        }
        List<Patient> patients = patientMapper.selectByExample(patientExample);
        List<Cost> costList = new ArrayList<>();
        for (Patient patient : patients) {
            Cost patientBill = getPatientBill(patient);
            patientBill.setPatient(patient);
            costList.add(patientBill);
        }
        PageInfo<Cost> pageInfo = new PageInfo<Cost>(costList);
        int total = patientMapper.selectTotal(name,no);
//        pageInfo.setPageSize(5);
        pageInfo.setTotal(total);
        return pageInfo;
    }

    /** 查询所有已出院病人所花费费用列表
     * @return
     */
    @Override
    public PageInfo<Cost> queryAllOut (String name, String no, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusEqualTo("3");//规定3是已出院的
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");//模糊查询
        }
        if(!StringUtils.isEmpty(no)){
            criteria.andNoLike("%"+no+"%");
        }
        List<Patient> patients = patientMapper.selectByExample(patientExample);


        List<Cost> costList = new ArrayList<>();
        for (Patient patient : patients) {
            Cost patientBill = getPatientBill(patient);
            patientBill.setPatient(patient);
            costList.add(patientBill);
        }
        PageInfo<Cost> PageInfo = new PageInfo<Cost>(costList);
        return PageInfo;
    }

    /** 查询所有审核出院的病人信息
     * @return 返回有六个值,前五个分别为退药费用,处方费用,出院费用,医嘱费用,余额 第六个值为当前病人
     */
    @Override
    public PageInfo<Cost> leaveHospital (String name, String no, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusEqualTo("4");//规定4是确认审核出院的
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");//模糊查询
        }
        if(!StringUtils.isEmpty(no)){
            criteria.andNoLike("%"+no+"%");
        }
        List<Patient> patients = patientMapper.selectByExample(patientExample);

        List<Cost> costList = new ArrayList<>();
        for (Patient patient : patients) {
            Cost patientBill = getPatientBill(patient);
            patientBill.setPatient(patient);
            costList.add(patientBill);
        }
        PageInfo<Cost> PageInfo = new PageInfo<Cost>(costList);
        return PageInfo;
    }

    /** 病人现金缴费
     * @param id
     * @param money
     */
    @Override
    public void updateMoney (Integer id, Float money,String status) {
        Patient patient = patientMapper.selectByPrimaryKey(id);
        if(patient.getBalance()!=null){
            patient.setBalance(patient.getBalance() + money);
        }else{
            patient.setBalance(money);
        }
        if(status!=null){
            patient.setNurseId(9);
            patient.setDeptId(9);
            patient.setStatus(status);
        }
        if(patient.getAppointmenttTime()==null){
            patient.setAppointmenttTime(new Date());
        }
        patientMapper.updateByPrimaryKeySelective(patient);

        //同时向住院账单表中添加一条数据,记录病人住院账单
        HospitalizationBill hospitalizationBill = new HospitalizationBill();
        hospitalizationBill.setStartTime(new Date());
        hospitalizationBill.setPatientId(id);
        hospitalizationBillMapper.insert(hospitalizationBill);

        //向缴费记录表中添加一条缴费数据
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setPatientId(id);
        paymentRecord.setType("1");
        paymentRecord.setUpdatemoney(money);
        paymentRecord.setTime(new Date());
        paymentRecordMapper.insert(paymentRecord);
    }


    /** 提取出来的方法,根据病人查询所产生的费用
     * @param patient 所需要查询费用的病人
     * @return
     */
    public Cost getPatientBill(Patient patient){
        Integer id = patient.getId();
        //获取退药的费用
        List<Float> floats1 = drugOutBillMapper.selectBillMoneyByPatientId(id,"1");
        float outTotal = 0.0f;
        for (Float aFloat : floats1) {
            outTotal+=aFloat;
        }
        //获取处方的费用
        List<Float> floats2 = prescriptionBillMapper.selectMoneyByPatientId(id,"1");
        float prescriptionTotal = 0.0f;
        for (Float aFloat : floats2) {
            prescriptionTotal+=aFloat;
        }
        //获取住院费用,此时应该是用住院申请表中的住院时间开始计算为住院时间
        HospitalizationBillExample hospitalizationBillExample = new HospitalizationBillExample();
        HospitalizationBillExample.Criteria criteria = hospitalizationBillExample.createCriteria();
        criteria.andPatientIdEqualTo(patient.getId());
        List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectByExample(hospitalizationBillExample);
        HospitalizationBill hospitalizationBill = null;
        int i = 0;
        if(hospitalizationBills.size()!=0){
             hospitalizationBill = hospitalizationBills.get(0);
            //出院也可以用这个方法
            if(hospitalizationBill.getStartTime()!=null){
                if(hospitalizationBill.getEndTime()!=null){
                    i = timeOperation(hospitalizationBill.getStartTime() ,hospitalizationBill.getEndTime());
                }else{
                    i = timeOperation(hospitalizationBill.getStartTime(), new Date());
                }
                if(hospitalizationBill.getPayDays()!=null){
                    i = i - hospitalizationBill.getPayDays();
                }
            }
        }
        float timeTotal = i * DAILYFEE;
        //获取医嘱的费用
        List<Float> floats3 = medicalAdviceBillMapper.selectMoneyByPatientId(id,"1");
        float medicalAdviceTotal = 0.0f;
        for (Float aFloat : floats3) {
            medicalAdviceTotal+=aFloat;
        }
        return new Cost(outTotal,prescriptionTotal,timeTotal,medicalAdviceTotal,patient.getBalance(),i);
    }

//    /** 工具方法 将五个费用包装成list
//     * @param outTotal 退药费用
//     * @param prescriptionTotal  处方费用
//     * @param timeTotal  出院费用
//     * @param medicalAdviceTotal  医嘱费用
//     * @param balance 余额
//     * @return
//     */
//    private List<Object> getFloats (float outTotal, float prescriptionTotal, float timeTotal, float medicalAdviceTotal,Float balance) {
//        List<Object> floats = new ArrayList<>();
//        floats.add(outTotal);
//        floats.add(prescriptionTotal);
//        floats.add(timeTotal);
//        floats.add(medicalAdviceTotal);
//        floats.add(balance);
//        return floats;
//    }


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

    @Override
    public PageInfo<PaymentRecord> queryPayment(Integer pageNum, Integer pageSize,Integer id){
        PageHelper.startPage(pageNum,pageSize);
        PaymentRecordExample paymentRecordExample = new PaymentRecordExample();
        PaymentRecordExample.Criteria criteria = paymentRecordExample.createCriteria();
        criteria.andPatientIdEqualTo(id);
        paymentRecordExample.setOrderByClause("time desc");
        List<PaymentRecord> paymentRecords = paymentRecordMapper.selectByExample(paymentRecordExample);
        PageInfo<PaymentRecord> pageInfo = new PageInfo<>(paymentRecords);
        return pageInfo;
    }

    @Override
    public List<Integer> countHospitalization ( ) {
        List<Integer> list = new ArrayList<>();

        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andStatusNotLike("3"); // 不像3,也就是住院病人
        List<Patient> patients = patientMapper.selectByExample(patientExample);
        list.add(patients.size());

        PatientExample patientExample1 = new PatientExample();
        PatientExample.Criteria criteria1 = patientExample1.createCriteria();
        criteria1.andStatusEqualTo("3"); // 像3,也就是出院病人
        List<Patient> patients1 = patientMapper.selectByExample(patientExample1);
        list.add(patients1.size());

        return list;
    }

    @Override
    public List<Float> countInBill () {
        List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectInPatientAll();
        for (HospitalizationBill hospitalizationBill : hospitalizationBills) {
            hospitalizationBill.setSpare2("1");
        }
        List<PrescriptionBill> prescriptionBills = prescriptionBillMapper.selectInPatientAll();
        List<MedicalAdviceBill> medicalAdvicesBills = medicalAdviceBillMapper.selectInPatientAll();
        List<DrugOutBill> drugBills = drugOutBillMapper.selectInPatientAll();
        return getPatientFee(hospitalizationBills,prescriptionBills,medicalAdvicesBills,drugBills);
    }

    @Override
    public List<Float> countOutBill ( ) {
        List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectOutPatientAll();
        List<PrescriptionBill> prescriptionBills = prescriptionBillMapper.selectOutPatientAll();
        List<MedicalAdviceBill> medicalAdvicesBills = medicalAdviceBillMapper.selectOutPatientAll();
        List<DrugOutBill> drugBills = drugOutBillMapper.selectOutPatientAll();
        return getPatientFee(hospitalizationBills,prescriptionBills,medicalAdvicesBills,drugBills);
    }

    @Override
    public List<Float> queryPersonalData (Integer id) {
        HospitalizationBillExample hospitalizationBillExample = new HospitalizationBillExample();
        HospitalizationBillExample.Criteria criteria = hospitalizationBillExample.createCriteria();
        criteria.andPatientIdEqualTo(id);
        List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectByExample(hospitalizationBillExample);
        List<Float> list = new ArrayList<>();
        //住院
        float hospitalization = 0;
        if(hospitalizationBills.size()!=0){
            HospitalizationBill hospitalizationBill = hospitalizationBills.get(0);
            int i = 0;
//            if(hospitalizationBill.getEndTime()==null){
//                i = timeOperation(hospitalizationBill.getStartTime(),new Date());
//            }else{
//                i = timeOperation(hospitalizationBill.getStartTime(),hospitalizationBill.getEndTime());
//            }
            if(hospitalizationBill.getPayDays()!=null){
                i = hospitalizationBill.getPayDays();
            }
            hospitalization += i * DAILYFEE;
        }
        list.add(hospitalization);
        //处方
        float prescriptionFee = 0;
        List<Float> floats = prescriptionBillMapper.selectMoneyByPatientId(id,"2");
        for (Float aFloat : floats) {
            prescriptionFee += aFloat;
        }
        list.add(prescriptionFee);
        //医嘱
        float orderFee = 0;
        List<Float> floats1 = medicalAdviceBillMapper.selectMoneyByPatientId(id,"2");
        for (Float aFloat : floats1) {
            orderFee += aFloat;
        }
        list.add(orderFee);
        //退药
        float outFee = 0;
        List<Float> floats2 = drugOutBillMapper.selectBillMoneyByPatientId(id,"2");
        for (Float aFloat : floats2) {
            outFee += aFloat;
        }
        list.add(outFee);
        return list;
    }

    @Override
    public List<Float> queryPersonalTime (Integer id) {
        return null;
    }

    @Override
    public List<Float> getAllPayCount (String startTime, String endTime) {
        HospitalizationBillExample hospitalizationBillExample = new HospitalizationBillExample();
        HospitalizationBillExample.Criteria criteria = hospitalizationBillExample.createCriteria();

        PrescriptionBillExample prescriptionBillExample = new PrescriptionBillExample();
        PrescriptionBillExample.Criteria criteria1 = prescriptionBillExample.createCriteria();

        MedicalAdviceBillExample medicalAdviceBillExample = new MedicalAdviceBillExample();
        MedicalAdviceBillExample.Criteria criteria2 = medicalAdviceBillExample.createCriteria();

        DrugOutBillExample drugOutBillExample = new DrugOutBillExample();
        DrugOutBillExample.Criteria criteria3 = drugOutBillExample.createCriteria();

        if(!StringUtils.isEmpty(startTime)){
            criteria.andSpare1GreaterThanOrEqualTo(startTime);
            criteria1.andSpare1GreaterThanOrEqualTo(startTime);
            criteria2.andSpare1GreaterThanOrEqualTo(startTime);
            criteria3.andSpare1GreaterThanOrEqualTo(startTime);
        }
        if(!StringUtils.isEmpty(endTime)) {
            criteria.andSpare1LessThanOrEqualTo(endTime);
            criteria1.andSpare1LessThanOrEqualTo(endTime);
            criteria2.andSpare1LessThanOrEqualTo(endTime);
            criteria3.andSpare1LessThanOrEqualTo(endTime);
        }
        List<HospitalizationBill> hospitalizationBills = hospitalizationBillMapper.selectByExample(hospitalizationBillExample);
        List<PrescriptionBill> prescriptionBills = prescriptionBillMapper.selectByExample(prescriptionBillExample);
        List<MedicalAdviceBill> medicalAdviceBills = medicalAdviceBillMapper.selectByExample(medicalAdviceBillExample);
        List<DrugOutBill> drugOutBills = drugOutBillMapper.selectByExample(drugOutBillExample);
        return getPatientFee(hospitalizationBills,prescriptionBills,medicalAdviceBills,drugOutBills);
    }

    @Override
    public PageInfo<Patient> getAllInPatient (Integer pageNum, Integer pageSize,Patient patient) {
        PageHelper.startPage(pageNum,pageSize);
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        if(StringUtils.isEmpty(patient.getName())){
            criteria.andNameLike("%"+patient.getName()+"%");
        }
        criteria.andStatusEqualTo("9");
        List<Patient> patients = patientMapper.selectByExample(patientExample);
        PageInfo<Patient> pageInfo = new PageInfo<>(patients);
        return pageInfo;
    }


    public List<Float> getPatientFee(List<HospitalizationBill> hospitalizationBills,
                                     List<PrescriptionBill> prescriptionBills,
                                     List<MedicalAdviceBill> medicalAdvicesBills,
                                     List<DrugOutBill> drugBills){
        List<Float> list = new ArrayList<>();
        //所有病人住院总费用
        float hospitalization = 0;
        for (HospitalizationBill hospitalizationBill : hospitalizationBills) {
            int i = 0;
            if(hospitalizationBill.getEndTime()==null){
                if(hospitalizationBill.getSpare2()!=null){
                    if(hospitalizationBill.getPayDays()!=null){
                        i = hospitalizationBill.getPayDays();
                    }else{
                        i = 0;
                    }
                }else{
                    i = timeOperation(hospitalizationBill.getStartTime(),new Date());
                }
            }else{
                i = timeOperation(hospitalizationBill.getStartTime(),hospitalizationBill.getEndTime());
            }
            hospitalization += i * DAILYFEE;
        }
        list.add(hospitalization);

        //所有病人的处方总费用
        float prescriptionFee = 0;
        for (PrescriptionBill prescriptionBill : prescriptionBills) {
            prescriptionFee += prescriptionBill.getMoney();
        }
        list.add(prescriptionFee);

        //所有病人的医嘱总费用
        float orderFee = 0;
        for (MedicalAdviceBill medicalAdviceBill : medicalAdvicesBills) {
            orderFee += medicalAdviceBill.getMoney();
        }
        list.add(orderFee);

        //所有病人的退药总费用
        float outFee = 0;
        for (DrugOutBill drugOutBill : drugBills) {
            outFee += drugOutBill.getMoney();
        }
        list.add(outFee);
        return list;
    }
}

