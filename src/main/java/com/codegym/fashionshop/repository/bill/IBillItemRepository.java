package com.codegym.fashionshop.repository.bill;


import com.codegym.fashionshop.entities.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IBillItemRepository extends JpaRepository<BillItem,Long> {
List<BillItem>findAllByBill_Customer_CustomerId(Long customerId);
}
