package com.codegym.fashionshop.service.customer.impl;

import com.codegym.fashionshop.entities.CustomerType;
import com.codegym.fashionshop.repository.customer.ICustomerTypeRepository;
import com.codegym.fashionshop.service.customer.ICustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing customer types.
 *
 * <p>This class provides methods to retrieve all customer types from the repository.</p>
 *
 * <p>Author: [QuyLV]</p>
 */

@Service
public class CustomerTypeService implements ICustomerTypeService {
    @Autowired
    private ICustomerTypeRepository iCustomerTypeRepository;
    /**
     * Retrieves all customer types from the repository.
     *
     * @return a list of {@link CustomerType} entities.
     */
    @Override
    public List<CustomerType> getAllCustomerTypes() {
        return iCustomerTypeRepository.findAll();
    }

    @Override
    public CustomerType findById(Long id) {
        return iCustomerTypeRepository.findById(id).orElse(null);
    }
}
