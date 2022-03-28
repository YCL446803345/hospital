package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.*;
import com.woniu.mapper.*;

import com.woniu.service.PrescriptionDrugService;

import com.woniu.service.PrescriptionService;
import com.woniu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.*;

/**
 * 处方业务层
 */
@Service("prescriptionService")
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    private SendDrugRecordMapper sendDrugRecordMapper;
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private PrescriptionDrugMapper prescriptionDrugMapper;
    @Autowired
    private DrugMapper drugMapper;

    //处方详情+模糊查询
    public List<Prescription> getPresDrugByMany(String doctorName, String nurseName,String preName) {

        List<Prescription> prescriptionList2= prescriptionMapper.getPreDrugByMany(doctorName,nurseName,preName);


        return prescriptionList2;
    }

    //药方撤销药方修改状态
    public void updateStatusById(Integer pid, String account) {
        Prescription prescription = new Prescription();
        prescription.setId(pid);
        prescription.setPrescriptionStatus(3);
        prescription.setSpare1(account);    //撤销人
        prescription.setSpare2(TimeUtil.getNowTime(new Date())); //撤销时间
        prescriptionMapper.updateByPrimaryKeySelective(prescription);
    }


    //药房批量修改处方状态，添加数据
    public LinkedHashSet<String> updateByBatch(List<String> newlist, String account) {
        Worker worker = workerMapper.selectWorkerByAccount(account);

        //用于存放库存不足的药品名
        LinkedHashSet<String> drugs =  new LinkedHashSet<>();
        //用于存放库存足够的处方,便于批量修改
        List<Prescription> prescriptionList = new ArrayList<>();
        //用于存放药房批量添加发药记录
        List<SendDrugRecord> sendDrugRecordList = new ArrayList<>();

        //迭代每个处方
        for (String s : newlist) {

            //计数器判断

            ArrayList count = new ArrayList();

            Integer i = Integer.parseInt(s);
            //修改之前先比较药品库存数量，如果不足就记录下来，如果可以就修改
            List<Prescription> stockNums = prescriptionMapper.byPidfindStockAndNum(i);
            //用于批量修改库存
            List<Prescription> updatestockNum = new ArrayList<>();
            for(Prescription p : stockNums){
                updatestockNum.add(p);
            }
            ArrayList<Integer> integers = new ArrayList<>();
            //进行比较
            for (int j = 0; j <stockNums.size();j++ ){
                Prescription prescription = stockNums.get(j);
                if (prescription.getNum() >= prescription.getStock()){
                    drugs.add(prescription.getDrugName());
                    count.add(false);
                    integers.add(j);
                }
            }
            //迭代器移除库存不足的药品,防止下标越界
            Iterator<Prescription> it = updatestockNum.iterator();
            for (Integer integer : integers) {
                while (it.hasNext()){
                    if (it.next().equals(updatestockNum.get(integer))){
                        it.remove();
                    }
                }
            }
            //计数器判断,如果包含false,就不执行这次处方的操作
            if (count.contains(false)){
                //跳出本次处方循环
                continue;
            }else {
                //药品库存大于发药数量可以进行发药修改操作
                Prescription prescription = new Prescription();
                prescription.setId(i);
                prescription.setPrescriptionStatus(2);
                prescription.setSpare1(worker.getName());
                prescription.setSpare2(TimeUtil.getNowTime(new Date()));
                prescriptionList.add(prescription);
                //同时添加发药记录
                SendDrugRecord sendDrugRecord = new SendDrugRecord();
                sendDrugRecord.setPrescriptionId(i);
                sendDrugRecord.setSendDrugTime(new Date());
                sendDrugRecord.setSpare1(worker.getName());
                sendDrugRecordList.add(sendDrugRecord);
                //批量修改库存
                if (updatestockNum.size() != 0){
                    for (Prescription updateStock : updatestockNum) {
                        Integer newStock = updateStock.getStock() - updateStock.getNum();
                        drugMapper.updateStock(newStock,updateStock.getDrugName());
                    }
                }
            }
        }

        if (prescriptionList.size() != 0){
            //迭代修改库存足够的处方信息
            for (Prescription prescription : prescriptionList) {
                prescriptionMapper.updateByPrimaryKeySelective(prescription);
            }
            //批量添加发药记录
            sendDrugRecordMapper.addSenDrugBatch(sendDrugRecordList);
        }

        return drugs;
    }

    //通过处方id查询该处方开单医生和审核护士和创建时刻
    public Prescription getDrugNameAndNumAndCreateTime(Integer pid) {
        Prescription prescription = prescriptionMapper.getPreInfo(pid);
        return prescription;

    }


    @Override
    public PageInfo<Prescription> getPrescriptions(Prescription prescription, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Prescription> prescriptions = prescriptionMapper.findPatients(prescription);
        PageInfo<Prescription> patientPageInfo = new PageInfo<>(prescriptions);
        return patientPageInfo;
    }

    @Override
    public void checkPrescription(Integer id) {
        prescriptionMapper.checkPrescription(id);
    }

    @Override
    public void doPrescription(Integer id) {
        prescriptionMapper.doPrescription(id);
    }

    @Override
    public void stopPrescription(Integer id) {
        prescriptionMapper.stopPrescription(id);
    }
    //分页模糊查询处方列表
    public PageInfo<Prescription> queryPrescriptionList(Prescription prescription, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Prescription> prescriptionList = prescriptionMapper.queryPrescriptionList(prescription);
        PageInfo<Prescription> prescriptionPageInfo = new PageInfo<>(prescriptionList);
        return prescriptionPageInfo;
		}

    @Override
    public void addPrescription(Prescription prescription) {
        //新增处方表数据
        prescription.setCreateTime(new Date());
        prescription.setPrescriptionStatus(1);
        prescriptionMapper.addPrescription(prescription);

        //新增处方药品中间表数据
        PrescriptionDrug prescriptionDrug = new PrescriptionDrug();
        prescriptionDrug.setPrescriptionId(prescription.getId());
        prescriptionDrug.setDrugId(prescription.getDrugId());
        prescriptionDrug.setNum(prescription.getNum());
        prescriptionDrugMapper.addPrescriptionDrug(prescriptionDrug);

    }
}
