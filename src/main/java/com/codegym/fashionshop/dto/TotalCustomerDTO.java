package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing total customer count and growth.
 * <p>
 * This class encapsulates the total number of customers and the growth percentage
 * compared to the previous period.
 * </p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalCustomerDTO {
    private long totalCustomers;

    private double growth;
}
