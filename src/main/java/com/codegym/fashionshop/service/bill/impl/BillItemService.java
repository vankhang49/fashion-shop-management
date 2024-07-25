package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.repository.bill.IBillItemRepository;
import com.codegym.fashionshop.service.bill.IBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BillItemService implements IBillItemService {
    @Autowired
    private IBillItemRepository repository;


    @Override
    public void save(BillItem billItem) {
        repository.save(billItem);
    }

    @Override
    public List<BillItem> findAllByBill_Customer_CustomerId(Long customerId) {
        return repository.findAllByBill_Customer_CustomerId(customerId);
    }

}
