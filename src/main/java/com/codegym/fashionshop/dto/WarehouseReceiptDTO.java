package com.codegym.fashionshop.dto;

import com.codegym.fashionshop.entities.Pricing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

/**
 * WarehouseReceiptDTO represents a warehouse receipt.
 * It contains the receipt ID, username of the person who created the receipt,
 * the date of the receipt, and a list of pricings associated with the receipt.
 *
 * @author ThanhTT
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceiptDTO {
    private String receiptId;
    private String username;
    private LocalDate date;
    private List<Pricing> pricingList;
}
