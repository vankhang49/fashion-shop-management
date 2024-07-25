package com.codegym.fashionshop.service.customer;

import com.codegym.fashionshop.entities.CustomerType;

import java.util.List;

/**
 * Service interface for managing customer types.
 *
 * <p>This interface defines methods for retrieving customer types from the repository.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
public interface ICustomerTypeService {

    /**
     * Retrieves all customer types from the repository.
     *
     * @return a list of {@link CustomerType} entities.
     */
    List<CustomerType> getAllCustomerTypes();
    CustomerType findById(Long id);
}
