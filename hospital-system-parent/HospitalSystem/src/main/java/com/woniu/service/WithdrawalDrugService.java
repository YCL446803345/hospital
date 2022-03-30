package com.woniu.service;

import com.woniu.entity.WithdrawalDrug;

import java.util.List;

public interface WithdrawalDrugService {
    List<WithdrawalDrug> findAllRecord(String name);
}
