package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The Customer class represents a customer in the system.
 * <p>
 * This class contains attributes and methods necessary to manage a customer's information.
 * Each customer has a unique code, name, date of birth, gender, email, phone number, address,
 * registration date, accumulated points, and customer type.
 * <p>
 * The attributes are validated to ensure the input data is correct.
 * <p>
 * Author: [QuyLV]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @NotBlank(message = "Mã khách hàng không được để trống !")
    @Pattern(regexp = "^KH-\\d{4,}$", message = "Mã khách hàng phải có định dạng KH-XXXX")
    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    @NotBlank(message = "Tên khách hàng không được để trống !")
    @Size(max = 50, message = "Tên khách hàng không quá 50 ký tự !")
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotNull(message = "Ngày sinh không được để trống !")
    @Past(message = "Ngày phải là ngày quá khứ !")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Giới tính không được để trống !")
    @Min(value = 0, message = "Giới tính nhỏ nhất là 0 !")
    @Max(value = 2, message = "Giới tính lớn nhất là 2 !")
    @Column(name = "gender")
    private Integer gender;

    @Email(message = "Email phải hợp lệ !")
    @NotBlank(message = "Email không được để trống !")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^(\\+84|0)\\d{9}$", message = "Số điện thoại phải bắt đầu bằng +84 hoặc 0 và kết thúc với 9 số!")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ không được để trống !")
    @Size(max = 255, message = "Tối đa 255 ký tự !" )
    @Column(name = "address")
    private String address;

    @Column(name = "date_register")
    private LocalDate dateRegister;

    @Column(name = "enable")
    private Boolean enable = true;

    @Min(value = 0, message = "Điểm phải lớn hơn hoặc bằng 0 !")
    @Column(name = "accumulated_points")
    private int accumulatedPoints;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CustomerType customerType;

}
