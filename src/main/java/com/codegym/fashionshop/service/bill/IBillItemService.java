package com.codegym.fashionshop.service.bill;

import com.codegym.fashionshop.entities.BillItem;

import java.util.List;

public interface IBillItemService  {
    void save(BillItem billItem);
    List<BillItem> findAllByBill_Customer_CustomerId(Long customerId);

}
