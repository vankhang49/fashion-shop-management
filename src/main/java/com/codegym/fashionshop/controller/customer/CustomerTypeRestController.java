package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.entities.CustomerType;
import com.codegym.fashionshop.service.customer.ICustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing customer types.
 *
 * <p>This controller provides endpoints to retrieve customer types.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
@RestController
@RequestMapping("/api/auth/customer-type")
public class CustomerTypeRestController {

    @Autowired
    private ICustomerTypeService iCustomerTypeService;

    /**
     * Retrieves all customer types.
     *
     * @return ResponseEntity with a list of customer types and HTTP status OK if successful.
     */
    @GetMapping("")
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes() {
        List<CustomerType> customerTypes = iCustomerTypeService.getAllCustomerTypes();
        return new ResponseEntity<>(customerTypes, HttpStatus.OK);
    }
}
