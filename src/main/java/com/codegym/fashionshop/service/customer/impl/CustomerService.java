package com.codegym.fashionshop.service.customer.impl;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.entities.CustomerType;
import com.codegym.fashionshop.repository.customer.ICustomerRepository;
import com.codegym.fashionshop.service.customer.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDate;


/**
 * The CustomerService class provides implementations for customer-related operations.
 *
 * <p>This class implements the ICustomerService interface and provides methods for creating,
 * updating, finding, and checking the existence of customers.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;

    /**
     * Creates a new customer with default values for customer type, accumulated points, and registration date.
     *
     * @param customer the customer to be created
     */
    @Override
    public void createCustomer(Customer customer) {
        customer.setCustomerType(new CustomerType(1L, "", 0D));
        customer.setAccumulatedPoints(0);
        customer.setDateRegister(LocalDate.now());

        iCustomerRepository.createCustomer(
                customer.getCustomerCode(),
                customer.getCustomerName(),
                customer.getDateOfBirth(),
                customer.getGender(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getDateRegister(),
                customer.getAccumulatedPoints()
        );
    }

    /**
     * Updates an existing customer with the specified details.
     *
     * @param id       the ID of the customer to be updated
     * @param id the ID of the customer to be updated
     * @param customer the updated customer data
     * @throws IllegalArgumentException if the email already exists or the customer with the specified ID does not exist
     */
    @Override
    public void updateCustomer(Long id, Customer customer) {
        if (iCustomerRepository.existsById(id)) {
            iCustomerRepository.updateCustomer(
                    id,
                    customer.getCustomerName(),
                    customer.getDateOfBirth(),
                    customer.getGender(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getAddress(),
                    customer.getCustomerType().getTypeId(),
                    customer.getAccumulatedPoints()
            );
        }
    }

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be retrieved
     * @return the customer with the specified ID, or null if no such customer exists
     */
    @Override
    public Customer findById(Long id) {
        return iCustomerRepository.findById(id).orElse(null);
    }

    /**
     * Finds all customers with pagination support.
     *
     * @param pageable the pagination information
     * @return a paginated list of customers
     */
    @Override
    public Page< Customer > findAll(Pageable pageable) {
        return iCustomerRepository.findAll(pageable);
    }

    /**
     * Checks if a customer with the specified customer code exists.
     *
     * @param customerCode the customer code
     * @return true if a customer with the specified customer code exists, false otherwise
     */
    @Override
    public boolean existsByCustomerCode(String customerCode) {
        return iCustomerRepository.existsByCustomerCode(customerCode);
    }

    /**
     * Checks if a customer with the specified email exists.
     *
     * @param email the email
     * @return true if a customer with the specified email exists, false otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return iCustomerRepository.existsByEmail(email);
    }

    /**
     * Checks if a customer exists with the given email but not with the specified customer code.
     *
     * @param email        the email address of the customer to check.
     * @param customerCode the customer code to exclude in the check.
     * @return {@code true} if a customer exists with the given email but not with the specified customer code, {@code false} otherwise.
     */
    @Override
    public boolean existsByEmailAndCustomerCodeNot(String email, String customerCode) {
        return iCustomerRepository.existsByEmailAndCustomerCodeNot(email, customerCode);
    }

    /**
     * Updates the enable status of a customer.
     *
     * This method sets the enable status of a customer identified by their customer ID.
     * The method is transactional, ensuring that the operation is atomic and consistent.
     *
     * @param customerId the ID of the customer whose enable status is to be updated
     * @param enabled the new enable status to be set for the customer
     */
    @Override
    public void deleteCustomer(Long customerId, Boolean enabled) {
        iCustomerRepository.deleteCustomer(customerId, enabled);
    }
    /**
     * Retrieves a paginated list of customers based on a search keyword.
     *
     * @param keyword the keyword to search for (can be part of customer name, code, etc.)
     * @param pageable the pagination information
     * @return a page of customers matching the search keyword
     */
    @Override
    public Page<Customer> getAllCustomers(String keyword, Pageable pageable) {
        return iCustomerRepository.findAllCustomerAndSearch(keyword, keyword, keyword, keyword, pageable);
    }
}
