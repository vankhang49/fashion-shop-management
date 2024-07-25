package com.codegym.fashionshop.dto.request;

import com.codegym.fashionshop.entities.AppRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a request to create or update an AppUser.
 * This includes various user details like username, password, contact information, and role.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest implements Validator {
    private Long userId;

    @NotBlank(message = "Tên đăng nhập không được để trống!")
    @Size(min = 4, max = 50, message = "Tên đăng nhập phải từ 4 đến 50 chữ!")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]*$", message="Tên đăng nhập không được bắt đầu bằng số và không được chứa các ký tự đặc biệt!")
    private String username;

    private String password;

    @NotBlank(message = "Mã nhân viên không được để trống!")
    @Pattern(regexp = "^NV\\d{4}$", message="Mã nhân viên phải được bắt đầu bằng NV và kết thúc với 4 chữ số!")
    private String userCode;

    private String backgroundImage;

    private String avatar;

    @NotBlank(message = "Tên không được để trống!")
    @Size(max = 50, message = "Họ và tên không được quá 50 Ký tự!")
    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹĂăÂâÊêÔôƠơƯưĐđ\\s]+$", message = "Tên nhân viên phải bắt đầu bằng chữ IN HOA, không được chứa ký tự số và không được chứa ký tự đặc biệt!")
    private String fullName;

    @NotNull(message = "Ngày sinh không được để trống!")
    private LocalDate dateOfBirth;

    @NotNull(message = "Giới tính không được bỏ trống!")
    private Integer gender;

    @NotBlank(message = "Số điện thoại không được bỏ trống!")
    @Pattern(regexp = "^(?:\\+84|0)\\d{9}$", message = "Số điện thoại phải bắt đầu bằng +84 hoặc 0 và kết thúc với 9 số!")
    private String phoneNumber;

    @Email(message = "Email không được để trống!")
    @NotBlank(message = "Email Không được để trống!")
    private String email;

    @NotBlank(message = "Địa chỉ Không được để trống!")
    @Length(min = 0, max = 255, message = "Địa chỉ không thể vượt quá tối đa 255 ký tự!")
    private String address;

    @NotNull(message = "Chức vụ Không được để trống!")
    private AppRole role;

    private LocalDate dateCreate;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    @Override
    public boolean supports(Class<?> clazz) {
        return AppUserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUserRequest appUserRequest = (AppUserRequest) target;
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfBirthCheck = appUserRequest.getDateOfBirth();
        int acceptYear = Period.between(dateOfBirthCheck, currentDate).getYears();
        if (acceptYear < 18) {

            errors.rejectValue("dateOfBirth","", "Ngày sinh phải lớn hơn ngày hiện tại 18 năm!!");
        }
    }
}
