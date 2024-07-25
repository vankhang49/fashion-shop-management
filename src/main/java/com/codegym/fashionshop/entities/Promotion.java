package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "promotion_name")
    @NotBlank(message = "Tên khuyến mãi không được để trống!")
    private String promotionName;
    @Column(name = "promotion_code")
//    @NotBlank(message = "Tên khuyến mãi không được để trống!")
    private String promotionCode;

    @Column(name = "discount")
//    @Min(value = 0, message = "tỉ lệ không được nhỏ hơn 0 và phải từ 0 đến 1!")
//    @Max(value = 1, message = "tỉ lệ không được lớn hơn 1 và phải từ 0 đến 1!")
    private Double discount;

    @Column(name = "start_day")
    private LocalDate startDay;

    @Column(name = "end_day")
    private LocalDate endDay;


    @Column(name = "quantity")
    private Integer quantity;
    /**
     * Indicates if the user account is enabled.
     */
    @Column(name = "enabled")
    private Boolean enabled;
    /**
     * Reduces the quantity by 1 and disables the promotion if quantity is 0.
     */
    public void usePromotion() {
        if(this.endDay != null && this.endDay.isBefore(LocalDate.now()))
        {
            this.enabled = false;//endDay nhỏ hơn ngày hiện tại
        }else {
            if (this.quantity > 0) {
                this.quantity--;
            }
            if (this.quantity == 0) {
                this.enabled = false; // Vô hiệu hóa nếu quantity = 0
            }
        }
    }
}