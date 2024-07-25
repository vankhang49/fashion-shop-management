package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SoldPricingsDTO represents the pricing details of sold items.
 * It contains the pricing code, pricing name, total quantity sold, and the price.
 *
 * @author ThanhTT
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoldPricingsDTO {
    private String pricingCode;
    private String pricingName;
    private int totalQuantity;
    private double price;
}
