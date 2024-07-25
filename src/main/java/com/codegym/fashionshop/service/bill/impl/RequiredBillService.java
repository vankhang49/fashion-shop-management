package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.entities.RequiredBill;
import com.codegym.fashionshop.repository.bill.IRequiredBillRepository;
import com.codegym.fashionshop.service.bill.IRequiredBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RequiredBillService implements IRequiredBillService {
    @Autowired
    IRequiredBillRepository requiredBillRepository;
    @Override
    public List<RequiredBill> findAll() {
        return requiredBillRepository.findAll();
    }

    @Override
    public RequiredBill findById(Long id) {
        return requiredBillRepository.findById(id).get();
    }
}
