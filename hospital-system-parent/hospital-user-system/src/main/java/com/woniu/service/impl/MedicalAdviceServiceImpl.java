package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.MedicalAdvice;
import com.woniu.mapper.MedicalAdviceMapper;
import com.woniu.service.MedicalAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MedicalAdviceServiceImpl implements MedicalAdviceService {
    @Autowired
    private MedicalAdviceMapper medicalAdviceMapper;



//    分页模糊查询医嘱列表
    public PageInfo<MedicalAdvice> queryMedicalAdvice(MedicalAdvice medicalAdvice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MedicalAdvice> medicalAdviceList = medicalAdviceMapper.queryMedicalAdviceList(medicalAdvice);
        PageInfo<MedicalAdvice> medicalAdvicePageInfo = new PageInfo<>(medicalAdviceList);
        return medicalAdvicePageInfo;
    }

    //申请医嘱
    public void addMedicalAdvice(MedicalAdvice medicalAdvice) {
        medicalAdvice.setDoctorId(4);
        medicalAdvice.setCreateTime(new Date());
        medicalAdvice.setAdviceStatus(1);
        medicalAdviceMapper.insert(medicalAdvice);
    }

    //修改医嘱
    public void updateMedicalAdvice(MedicalAdvice medicalAdvice) {
        medicalAdviceMapper.updateByPrimaryKey(medicalAdvice);
    }

    //删除医嘱
    public void deleteMedicalAdviceById(Integer id) {
        medicalAdviceMapper.deleteByPrimaryKey(id);
    }

    //根据id查询医嘱
    public MedicalAdvice queryMedicalAdviceById(Integer id) {
        return medicalAdviceMapper.selectByPrimaryKey(id);
    }

    //医生站根据Id查询医嘱(关联查询详细)
    public MedicalAdvice queryMedicalAdviceById2(Integer id) {
        return medicalAdviceMapper.findMedicalAdviceById(id);
    }


}
