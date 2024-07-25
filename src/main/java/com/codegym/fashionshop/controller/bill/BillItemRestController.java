package com.codegym.fashionshop.controller.bill;

import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.bill.IBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing bill item-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/bill-items")
public class BillItemRestController {

    @Autowired
    private IBillItemService billItemService;

    /**
     * POST endpoint to create a new bill item.
     *
     * @param billItem       the bill item object to be created (validated using @Validated)
     * @param bindingResult  captures and exposes errors from binding/validation process
     * @return a ResponseEntity containing the created bill item and HTTP status CREATED (201) if successful
     * @throws HttpExceptions.BadRequestException if there are validation errors in the request body
     */
    @PostMapping("")
    public ResponseEntity<Object> createBillItem(@Validated @RequestBody BillItem billItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        billItemService.save(billItem);
        return new ResponseEntity<>(billItem, HttpStatus.CREATED);
    }
}
