package com.codegym.fashionshop.controller.bill;


import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.*;
import com.codegym.fashionshop.dto.DailyRevenueDTO;
import com.codegym.fashionshop.dto.SoldPricingsDTO;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.entities.Bill;

import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
import com.codegym.fashionshop.service.bill.IBillService;
import com.codegym.fashionshop.service.product.IPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * REST controller for managing bill-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/bills")
public class BillRestController {

    @Autowired
    private IBillService billService;
    @Autowired
    private IAppUserService userService;
    @Autowired
    private IPricingService pricingService;

    /**
     * GET endpoint to retrieve all bills.
     *
     * @return a ResponseEntity containing a list of bills and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no bills are found
     */
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.findAll();
        if (bills.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin hóa đơn");
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new bill.
     *
     * @param bill          the bill object to be created (validated using @Validated)
     * @param bindingResult captures and exposes errors from binding/validation process
     * @return a ResponseEntity containing the created bill and HTTP status CREATED (201) if successful
     * @throws HttpExceptions.BadRequestException if there are validation errors in the request body
     */
    @PostMapping("")
    public ResponseEntity<Object> createBill(@Validated @RequestBody Bill bill, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        System.out.println(username);
        for (BillItem billItem : bill.getBillItemList()) {
            billItem.setBill(bill);
            // Update the quantity in the Pricing entity
            pricingService.updateQuantity(billItem.getPricing().getPricingId(), billItem.getQuantity());
        }
        int pointsToAdd = calculatePoints(bill);
        bill.setAppUser(user);
        billService.createBillAndUpdatePoints(bill, pointsToAdd);
        billService.updateCustomerTypeOfCustomer(bill);

        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    /**
     * POST endpoint to generate and check a unique bill code.
     *
     * @return a ResponseEntity containing a map with the generated bill code and HTTP status OK (200) if successful
     */
    @PostMapping("/generateAndCheckBillCode")
    public ResponseEntity<Map<String, String>> generateAndCheckBillCode() {
        String billCode = generateUniqueBillCode();
        Map<String, String> response = new HashMap<>();
        response.put("code", billCode);
        return ResponseEntity.ok(response);
    }

    /**

     * Retrieves the daily sales revenue for a specific date.
     *
     * @param date The date for which to retrieve the daily sales revenue.
     * @return A ResponseEntity containing the daily sales revenue.
     * @author ThanhTT
     */
    @GetMapping("/revenue/daily")
    public ResponseEntity<Double> getDailySalesRevenue(@RequestParam("date") LocalDate date) {
        Double dailyRevenue = billService.getDailySalesRevenue(date);
        if (dailyRevenue == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dailyRevenue, HttpStatus.OK);
    }
    /**
     * Retrieves the monthly sales revenue for a specific month.
     *
     * @param yearMonth The YearMonth object for which to retrieve the monthly sales revenue.
     * @return A ResponseEntity containing the monthly sales revenue.
     * @author ThanhTT
     */
    @GetMapping("/revenue/monthly")
    public ResponseEntity<Double> getMonthlySalesRevenue(@RequestParam("month") YearMonth yearMonth) {
        Double monthlyRevenue = billService.getMonthlySalesRevenue(yearMonth);
        if (monthlyRevenue == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);
    }
    /**
     * Retrieves the daily sales revenue for each day in a specific month.
     *
     * @param yearMonth The YearMonth object for which to retrieve the daily sales revenue.
     * @return A ResponseEntity containing a map of the day and the corresponding daily sales revenue.
     * @author ThanhTT
     */
    @GetMapping("/revenue/daily/month")
    public ResponseEntity<List<DailyRevenueDTO>> getDailySalesRevenueForMonth(@RequestParam("month") YearMonth yearMonth) {
        List<DailyRevenueDTO> dailyRevenue = billService.getDailySalesRevenueForMonth(yearMonth);
        if (dailyRevenue.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dailyRevenue, HttpStatus.OK);
    }
    /**
     * Generates a unique bill code.
     *
     * @return a unique bill code prefixed with "HD" (e.g., HD000001)
     */
    private String generateUniqueBillCode() {
        String billCode;
        Random random = new Random();
        do {
            billCode = "HD-" + String.format("%06d", random.nextInt(1000000));
        } while (!billService.isBillCodeUnique(billCode));
        return billCode;
    }
    /**
     * Calculates points to be added based on the bill.
     *
     * @param bill the bill object
     * @return the calculated points
     */
    private int calculatePoints(Bill bill) {
        double sum = 0;
        for (BillItem billItem : bill.getBillItemList()) {
            sum += billItem.getPricing().getPrice() * billItem.getQuantity();
        }
        return (int) (sum / 100000);
    }

    /**
     * Retrieves daily sold pricings for a given date.
     *
     * @param date the date for which to retrieve sold pricings
     * @return a ResponseEntity containing a list of SoldPricings or a status indicating no content or bad request
     * @author ThanhTT
     */
    @GetMapping("/sold-pricings/daily")
    public ResponseEntity<?> getDailySoldPricings(@RequestParam("date") LocalDate date) {
        try {
            List<SoldPricingsDTO> soldPricings = billService.getDailySoldPricings(date);
            if (soldPricings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(soldPricings, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Invalid date format. Please use yyyy-MM-dd.", HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Retrieves monthly sold pricings for a given year and month.
     *
     * @param yearMonth the YearMonth for which to retrieve sold pricings
     * @return a ResponseEntity containing a list of SoldPricings or a status indicating no content or bad request
     * @author ThanhTT
     */
    @GetMapping("/sold-pricings/monthly")
    public ResponseEntity<?> getMonthlySoldPricings(@RequestParam("month") YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();
        if (month < 1 || month > 12) {
            return new ResponseEntity<>("Invalid month value. Please provide a month between 1 and 12.", HttpStatus.BAD_REQUEST);
        }
        List<SoldPricingsDTO> soldPricings = billService.getDailySoldPricings(year, month);
        if (soldPricings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soldPricings, HttpStatus.OK);
    }
}
