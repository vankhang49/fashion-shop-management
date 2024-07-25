package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DailyRevenueDTO represents the daily revenue data.
 * It contains the day of the month and the revenue for that day.
 *
 * @author ThanhTT
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyRevenueDTO {
    private int day;
    private double revenue;
}
