package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for the best salesperson.
 * <p>
 * This class encapsulates the details of the best salesperson,
 * including their full name, revenue generated, and quantity sold.
 * </p>
 *
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestSalespersonDTO {
    private String fullName;

    private double revenue;

    private int quantity;
}
