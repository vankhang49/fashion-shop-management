package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**
 * Data Transfer Object (DTO) for representing new bill information.
 * <p>
 * This class encapsulates data related to a new bill, including the customer's name
 * and the date of creation.
 * </p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewBillDTO {
    private String customerName;

    private LocalDate dateCreate;
}
