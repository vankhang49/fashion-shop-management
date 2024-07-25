package com.codegym.fashionshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pricings")
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pricing_id")
    private Long pricingId;

    @Column(name = "pricing_name", nullable = false)
    @NotBlank(message = "Tên giá không được để trống!")
    @Size(min = 3, max = 50, message = "Tên giá phải có từ 3 đến 50 ký tự.")
    private String pricingName;

    @Column(name = "pricing_code", nullable = false, unique = true)
    @NotBlank(message = "Mã giá không được để trống!")
    @Pattern(regexp = "^H-\\d{6,}$", message = "Mã hóa đơn phải có định dạng H-XXXXXX")
    private String pricingCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    @NotNull(message = "Sản phẩm không được để trống!")
    private Product product;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Giá thành không được để trống!")
    @Min(value = 0, message = "Giá thành phải lớn hơn hoặc bằng 0!")
    private Double price;

    @Column(name = "size", nullable = false)
    @NotBlank(message = "Kích thước không được để trống!")
    private String size;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "quantity", columnDefinition = "integer default 0", nullable = false)
    @NotNull(message = "Số lượng tồn kho không được để trống!")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0!")
    private Integer quantity = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "color_id")
    @NotNull(message = "Màu sắc không được để trống!")
    private Color color;

    @Column(name = "pricing_image", nullable = false)
    @NotBlank(message = "Đường dẫn ảnh giá không được để trống!")
    private String pricingImgUrl;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Column(name = "enabled" ,nullable = false)
    private Boolean enabled=true;

}
