package com.codegym.fashionshop.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_code", unique = true)
    @NotBlank(message = "Mã sản phẩm không được để trống!")
    @Pattern(regexp = "^P-\\d{6,}$", message = "Mã hóa đơn phải có định dạng P-XXXXXX")
    private String productCode;

    @Column(name = "product_name")
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    @Size(min = 3, max = 50, message = "Tên sản phẩm phải có từ 3 đến 50 ký tự.")
    private String productName;

    @Column(name = "product_desc", columnDefinition = "TEXT")
    @NotBlank(message = "Mô tả sản phẩm không được để trống!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "Loại sản phẩm không được để trống!")
    private ProductType productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pricing> pricingList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImage> productImages;

    @Column(name = "enabled" ,nullable = false)
    private Boolean enabled=true;
}
