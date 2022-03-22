package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.MedicalAdvice;

import java.util.List;

public interface MedicalAdviceService {

    //分页模糊查询医嘱列表
    PageInfo<MedicalAdvice> queryMedicalAdvice(MedicalAdvice medicalAdvice, Integer pageNum, Integer pageSize);

    //申请医嘱
    void addMedicalAdvice(MedicalAdvice medicalAdvice);
    //修改医嘱
    void updateMedicalAdvice(MedicalAdvice medicalAdvice);
    //删除医嘱
    void deleteMedicalAdviceById(Integer id);
    //根据id查询医嘱
    MedicalAdvice queryMedicalAdviceById(Integer id);
    //根据ID查询医嘱(关联查询)
    MedicalAdvice queryMedicalAdviceById2(Integer id);


}
