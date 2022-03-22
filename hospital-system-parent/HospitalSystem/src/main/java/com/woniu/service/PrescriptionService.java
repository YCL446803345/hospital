package com.woniu.service;

import com.woniu.entity.Prescription;

import java.util.List;

/**
 * 处方接口
 */
public interface PrescriptionService {
    List<Prescription> findAllPre(Prescription prescription);

}
