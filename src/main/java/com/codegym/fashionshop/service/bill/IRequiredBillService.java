package com.codegym.fashionshop.service.bill;

import com.codegym.fashionshop.entities.RequiredBill;

import java.util.List;

public interface IRequiredBillService {
    List<RequiredBill> findAll();
    RequiredBill findById(Long id);
}
