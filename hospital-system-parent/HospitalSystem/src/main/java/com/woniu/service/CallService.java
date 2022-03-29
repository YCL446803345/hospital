package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Call;

public interface CallService {
    PageInfo<Call> getCallPatient (Integer pageNum, Integer pageSize, Call call);

    void updateCallStatus (Integer id);
}
