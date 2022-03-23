package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.ConsultationApplication;
import com.woniu.entity.MedicalAdvice;
import com.woniu.mapper.ConsultationApplicationMapper;
import com.woniu.service.ConsultationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
