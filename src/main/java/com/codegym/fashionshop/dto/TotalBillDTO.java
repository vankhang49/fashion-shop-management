package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class representing total bill count and growth.
 * This class is used to encapsulate data retrieved from the database
 * regarding total bill count and growth percentage.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalBillDTO {
    private long totalBills;

    private double growth;
}
