package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_types")
public class CustomerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "discount")
    @Min(value = 0, message = "tỉ lệ không được nhỏ hơn 0 và phải từ 0 đến 1!")
    @Max(value = 1, message = "tỉ lệ không được lớn hơn 1 và phải từ 0 đến 1!")
    private Double discount;

}
