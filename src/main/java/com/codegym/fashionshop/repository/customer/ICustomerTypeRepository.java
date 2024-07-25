package com.codegym.fashionshop.repository.customer;

import com.codegym.fashionshop.entities.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
