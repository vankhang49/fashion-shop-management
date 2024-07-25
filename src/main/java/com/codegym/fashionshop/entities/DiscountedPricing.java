package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "discounted_pricings")
public class DiscountedPricing {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "discounted_pricing_id")
//    private Long discountedPricingId;
//
//    @Column(name = "discounted_pricing_name")
//    private String discountedPricingName;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pricing_id", nullable = false)
//    private Pricing pricing;
//
//    @Column(name = "discount_percentage")
//    @Min(value = 0, message = "Tỷ lệ giảm giá phải lớn hơn hoặc bằng 0!")
//    private Double discountPercentage = 0.0;
//
//    @Column(name = "discounted_price")
//    private Double discountedPrice;
//
//    @Column(name = "start_day")
//    private LocalDate startDay;
//
//
//    // Calculate the discounted price based on the discount percentage
//    public Double calculateDiscountedPrice() {
//        if (discountPercentage != null && discountPercentage > 0) {
//            discountedPrice = pricing.getPrice() * (1 - discountPercentage / 100);
//        } else {
//            discountedPrice = pricing.getPrice();
//        }
//        return discountedPrice;
//    }
}
