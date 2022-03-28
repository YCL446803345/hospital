package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.ConsultationApplication;
import com.woniu.entity.MedicalAdvice;
import com.woniu.mapper.ConsultationApplicationMapper;
import com.woniu.service.ConsultationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("consultationApplicationService")
public class ConsultationApplicationServiceImpl implements ConsultationApplicationService {
    @Autowired
    private ConsultationApplicationMapper consultationApplicationMapper;

    //分页模糊查询会诊列表
    public PageInfo<ConsultationApplication> queryConsultationApplication(ConsultationApplication consultationApplication, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ConsultationApplication> consultationApplicationList = consultationApplicationMapper.queryConsultationApplicationList(consultationApplication);
        PageInfo<ConsultationApplication> consultationApplicationPageInfo = new PageInfo<>(consultationApplicationList);
        return consultationApplicationPageInfo;
    }

    //编辑会诊信息
    public void updateConsultationApplication(ConsultationApplication consultationApplication) {
        consultationApplicationMapper.updateConsultationApplication(consultationApplication);
    }

    //取消会诊
    public void gotoCancelConsultationApplicationById(Integer id) {
        consultationApplicationMapper.cancelConsultationApplication(id);
    }


    //突发情况申请会诊
    public void addConsultationApplication(ConsultationApplication consultationApplication) {
        consultationApplication.setConsultationDate(new Date());
        consultationApplication.setStatus("1");
        consultationApplicationMapper.addConsultationApplication(consultationApplication);
    }

}
