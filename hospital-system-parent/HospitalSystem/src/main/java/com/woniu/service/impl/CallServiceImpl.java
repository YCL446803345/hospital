package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Call;
import com.woniu.entity.CallExample;
import com.woniu.entity.Patient;
import com.woniu.mapper.CallMapper;
import com.woniu.service.CallService;
import com.woniu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CallServiceImpl implements CallService {

    @Autowired
    private CallMapper callMapper;

    @Override
    public PageInfo<Call> getCallPatient (Integer pageNum, Integer pageSize, Call call) {
        PageHelper.startPage(pageNum,pageSize);
        String nowTime = null;
        if(!StringUtils.isEmpty(call.getAppointmentTime())){
             nowTime = TimeUtil.getNowTime(call.getAppointmentTime());
        }
        List<Call> calls = callMapper.selectByCall(call.getDeptId(), nowTime);
        PageInfo<Call> pageInfo = new PageInfo<>(calls);
        return pageInfo;
    }

    @Override
    public void updateCallStatus (Integer id) {
        Call call = new Call();
        call.setId(id);
        call.setStatus("2");
        callMapper.updateByPrimaryKeySelective(call);
    }
}
