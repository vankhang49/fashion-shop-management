package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.*;
import com.codegym.fashionshop.service.dashboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing dashboard data.
 * This class provides endpoints for retrieving various statistics and data for the dashboard.
 * <p>
 * Author: KhangDV
 */
@RestController
@RequestMapping("/api/auth/dashboard")
public class DashboardController {
    @Autowired
    private TotalCustomerService totalCustomerService;

    @Autowired
    private TotalBillService totalBillService;

    @Autowired
    private RevenuesService revenuesService;

    @Autowired
    private BestSalespersonService salespersonService;

    @Autowired
    private NewBillService newBillService;

    /**
     * Retrieves total customer count and growth data.
     *
     * @return A {@link ResponseEntity} containing the {@link TotalCustomerDTO}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/total-customer")
    public ResponseEntity<TotalCustomerDTO> getTotalCustomer() {
        return ResponseEntity.ok(totalCustomerService.getTotalCustomerAndGrowth());
    }

    /**
     * Retrieves total bill count and growth data.
     *
     * @return A {@link ResponseEntity} containing the {@link TotalBillDTO}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/total-bill")
    public ResponseEntity<TotalBillDTO> getTotalBill() {
        return ResponseEntity.ok(totalBillService.getTotalBillAndGrowth());
    }

    /**
     * Retrieves revenue data based on the specified option.
     *
     * @param option The option indicating the time period for revenue data.
     * @return A {@link ResponseEntity} containing the {@link RevenueDTO}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/revenues/{option}")
    public ResponseEntity<RevenueDTO> getRevenues(@PathVariable(name = "option") int option) {
        return ResponseEntity.ok(revenuesService.getRevenueData(option));
    }

    /**
     * Retrieves the list of best salespersons.
     *
     * @return A {@link ResponseEntity} containing a list of {@link BestSalespersonDTO}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/best-salesperson")
    public ResponseEntity<List<BestSalespersonDTO>> getBestSalespersons() {
        return ResponseEntity.ok(salespersonService.getBestSalespersons());
    }

    /**
     * Retrieves the list of new bills.
     *
     * @return A {@link ResponseEntity} containing a list of {@link NewBillDTO}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/new-bills")
    public ResponseEntity<List<NewBillDTO>> getNewBills() {
        return ResponseEntity.ok(newBillService.getAllNewBills());
    }
}
