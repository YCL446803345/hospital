package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.ConsultationApplication;

public interface ConsultationApplicationService {

    //分页模糊查询会诊列表
    PageInfo<ConsultationApplication> queryConsultationApplication(ConsultationApplication consultationApplication, Integer pageNum, Integer pageSize);

    //编辑会诊信息
    void updateConsultationApplication(ConsultationApplication consultationApplication);
}
