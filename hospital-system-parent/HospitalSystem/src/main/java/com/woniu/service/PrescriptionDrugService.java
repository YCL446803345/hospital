package com.woniu.service;

import com.woniu.entity.PrescriptionDrug;

import java.util.List;

public interface PrescriptionDrugService  {
    List<PrescriptionDrug> getDrugNameAndNumByPreId(Integer pid);

}
