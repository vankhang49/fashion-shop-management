package com.codegym.fashionshop.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for adding a new notification.
 * Contains the information needed to create a notification.
 *
 * <p>Validation annotations are used to ensure that the data adheres to required constraints.</p>
 *
 * @author NhiNTY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewNotificationDTO {

    /**
     * The content of the notification.
     * This field cannot be null or blank.
     */
    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    /**
     * The topic of the notification.
     * This field cannot be null or blank and must not exceed 255 characters.
     */
    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 255, message = "Topic must be at most 255 characters long")
    String topic;

    /**
     * The date and time when the notification was created.
     * This field cannot be empty and must be in the format "yyyy-MM-dd hh:mm:ss".
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_date")
    LocalDateTime createDate;

    /**
     * List of role IDs that should receive the notification.
     * This field cannot be empty and must contain at least one role ID.
     */
    @NotEmpty(message = "At least one role ID must be provided")
    List<Long> listRole;
}
