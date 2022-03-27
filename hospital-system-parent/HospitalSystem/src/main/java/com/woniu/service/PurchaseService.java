package com.woniu.service;

import com.woniu.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    void addPurchase(Purchase purchase);

    List<Purchase> findAllPur(Purchase purchase);
}
