package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

/**
 * The CustomerRestController class handles HTTP requests for managing customers.
 *
 * <p>This class provides RESTful endpoints for creating, updating, and retrieving customer information.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
@RestController
@RequestMapping("/api/auth/customer")
public class CustomerRestController {

    @Autowired
    private ICustomerService iCustomerService;

    private final Random random = new Random();

    /**
     * Creates a new customer.
     *
     * @param customer      the customer to be created
     * @param bindingResult the result of the validation binding
     * @param customer       the customer to be created
     * @param bindingResult  the result of the validation binding
     * @return ResponseEntity with the result of the creation operation
     * @throws HttpExceptions.BadRequestException if validation errors occur or customer code/email already exists
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity< Object > createCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (iCustomerService.existsByCustomerCode(customer.getCustomerCode())) {
            ErrorDetail errors = new ErrorDetail("Customer code already exists");
            errors.addError("customerCode", "Mã khách hàng đã tồn tại !");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (iCustomerService.existsByEmail(customer.getEmail())) {
            ErrorDetail errors = new ErrorDetail("Email already exists");
            errors.addError("email", "Email đã tồn tại !");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }

    /**
     * Updates an existing customer.
     *
     * @param id            the ID of the customer to be updated
     * @param customer      the updated customer data
     * @param bindingResult the result of the validation binding
     * @param id             the ID of the customer to be updated
     * @param customer       the updated customer data
     * @param bindingResult  the result of the validation binding
     * @return ResponseEntity with the result of the update operation
     * @throws HttpExceptions.BadRequestException if validation errors occur or email already exists
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (iCustomerService.existsByEmailAndCustomerCodeNot(customer.getEmail(), customer.getCustomerCode())) {
            ErrorDetail errors = new ErrorDetail("Email already exists");
            errors.addError("email", "Email đã tồn tại !");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        iCustomerService.updateCustomer(id, customer);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.CREATED);
    }

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be retrieved
     * @return ResponseEntity with the customer data if found, otherwise NOT_FOUND status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findByIdCustomer(@PathVariable Long id) {
        Customer customer = iCustomerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Deletes a customer by updating their enable status to false.
     *
     * This method handles HTTP DELETE requests for deleting a customer. It validates the request and, if there are
     * validation errors, returns a response with error details. Otherwise, it proceeds to delete the customer
     * by setting their enable status to false.
     *
     * @param customerId the ID of the customer to be deleted
     * @return a ResponseEntity containing an error detail in case of validation errors, or an OK status if the
     *         customer was deleted successfully
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PatchMapping("/{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId) {
        iCustomerService.deleteCustomer(customerId, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves a paginated list of customers based on a search keyword, sort criteria, and pagination information.
     *
     * @param keyword the keyword to search for (can be part of customer name, code, etc.)
     * @param sortBy the field to sort the results by
     * @param ascending whether the sorting should be in ascending order
     * @param page the page number to retrieve (0-indexed)
     * @return a response entity containing a page of customers matching the search criteria
     */
    @GetMapping("")
    public ResponseEntity<Page<Customer>> getAllCustomer(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        if (page <= 0) {
            page = 0;
        }
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        }

        Page<Customer> customerList = iCustomerService.getAllCustomers(keyword, PageRequest.of(page, 10, sort));
        if (customerList.isEmpty()) {
            customerList = Page.empty();
            return new ResponseEntity<>(customerList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    /**
     * Generates an auto-generated customer code that is unique in the database.
     * The generated code format is "KH-" followed by a 4-digit random number.
     * Checks if the generated code already exists in the database and generates
     * a new code if necessary until a unique code is found.
     *
     * @return ResponseEntity containing the auto-generated customer code if successful.
     */
    @GetMapping("/code-auto")
    public ResponseEntity<String> autoCodeCustomer() {
        String codeAuto;
        do {
            int randomNumber = random.nextInt(10000);
            codeAuto = "KH-" + String.format("%04d", randomNumber);
        } while (iCustomerService.existsByCustomerCode(codeAuto));
        return new ResponseEntity<>(codeAuto, HttpStatus.OK);
    }
}
