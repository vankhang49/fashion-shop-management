package com.codegym.fashionshop.repository.bill;

import com.codegym.fashionshop.entities.RequiredBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRequiredBillRepository extends JpaRepository<RequiredBill,Long> {
}
