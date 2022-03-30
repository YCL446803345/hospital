package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.InHospitalTable;
import com.woniu.entity.Patient;
import com.woniu.mapper.InHospitalTableMapper;
import com.woniu.mapper.PatientMapper;
import com.woniu.service.InHospitalTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class InHospitalTableServiceImpl implements InHospitalTableService {

    @Autowired
    private InHospitalTableMapper inHospitalTableMapper;
    @Autowired
    private PatientMapper patientMapper;


    //分页查询预约住院申请列表
    public PageInfo<InHospitalTable> queryInHospitalTable(InHospitalTable inHospitalTable, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InHospitalTable> inHospitalTableList = inHospitalTableMapper.queryInHospitalTableList(inHospitalTable);
        PageInfo<InHospitalTable> inHospitalTablePageInfo = new PageInfo<>(inHospitalTableList);
        return inHospitalTablePageInfo;
    }

    //审核预约住院信息，新增患者信息
    public void updateInHospitalTable(InHospitalTable inHospitalTable) {
        //随机生成no编号
        String val ="";
        Random random = new Random();
        for (int i=0;i< 10;i++){
            val +=String.valueOf(random.nextInt(10));
        }
        String no = "no" + val;
        Patient patient = new Patient();
        patient.setName(inHospitalTable.getPatientName());
        patient.setNo(no);
        patient.setAge(inHospitalTable.getPatientAge());
        patient.setCardId(inHospitalTable.getCardId());
        patient.setPhone(inHospitalTable.getTelephone());
        patient.setGender(inHospitalTable.getPatientSex());
        patient.setDeptId(inHospitalTable.getDeptId());
        patient.setBaseDesc(inHospitalTable.getReason());
        patient.setStatus("9");
        //审核预约申请,新增患者列表信息
        patientMapper.addPatientByDoctor(patient);
        //审核预约通过,修改预约状态
        inHospitalTableMapper.updateInHospitalTable(inHospitalTable);
    }

    //添加住院通知单
    public void addInHospitalTable(InHospitalTable inHospitalTable) {
        inHospitalTable.setInHosptialTime(new Date());
        inHospitalTable.setStatus("1");
        inHospitalTableMapper.addInHospitalTable(inHospitalTable);
    }

    //驳回住院申请单
    public void gotoDeleteInHospitalTableById(Integer id) {
        inHospitalTableMapper.deleteByPrimaryKey(id);
    }
}
