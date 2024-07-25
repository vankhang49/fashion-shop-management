package com.codegym.fashionshop.controller.notification;

import com.codegym.fashionshop.dto.AddNewNotificationDTO;
import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.service.notification.INotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing notifications.
 * Provides endpoints for creating, fetching, and updating notifications.
 * Accessible to authenticated users with appropriate roles.
 *
 * @author NhiNTY
 */
@RestController
@RequestMapping("/api/auth/notification")
public class NotificationController {

    private final INotificationService notificationService;
    private static final String FETCH_ERROR_MESSAGE = "An error occurred while fetching notifications";

    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Retrieves a list of all notifications for the current user.
     *
     * @return ResponseEntity containing a list of notifications or an error message.
     */
    @GetMapping("/list")
    public ResponseEntity<Object> findAllNotification() {
        try {
            AuthenticationResponse response = notificationService.responseAuthentication();
            List<INotificationDTO> notifications = notificationService.getAllNotification(response.getRole().getRoleId(), response.getUserId());
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FETCH_ERROR_MESSAGE);
        }
    }

    /**
     * Retrieves a list of notifications filtered by read status.
     *
     * @param statusRead boolean indicating the read status of notifications to retrieve.
     * @return ResponseEntity containing a list of notifications or an error message.
     */
    @GetMapping("/listByStatusRead/{statusRead}")
    public ResponseEntity<Object> findAllNotificationByStatusRead(@PathVariable("statusRead") boolean statusRead) {
        try {
            AuthenticationResponse response = notificationService.responseAuthentication();
            List<INotificationDTO> notifications = notificationService.findNotificationsByStatusRead(response.getUserId(), statusRead);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FETCH_ERROR_MESSAGE);
        }
    }

    /**
     * Creates a new notification.
     *
     * @param addNewNotificationDTO Data transfer object containing the details of the new notification.
     * @param bindingResult         Binding result containing validation errors, if any.
     * @return ResponseEntity indicating the result of the creation operation or validation errors.
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<Object> createNotification(@Validated @RequestBody AddNewNotificationDTO addNewNotificationDTO, BindingResult bindingResult) {
        if (addNewNotificationDTO == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        notificationService.addNotification(
                addNewNotificationDTO.getContent(),
                addNewNotificationDTO.getCreateDate(),
                addNewNotificationDTO.getTopic(),
                addNewNotificationDTO.getListRole());
        return new ResponseEntity<>("Notification created successfully", HttpStatus.CREATED);
    }

    /**
     * Retrieves a notification by its ID and marks it as read.
     *
     * @param notifId ID of the notification to retrieve.
     * @return ResponseEntity containing the notification or an error message.
     */
    @GetMapping("/getInfoNotification/{notifId}")
    public ResponseEntity<Object> findNotificationById(@PathVariable("notifId") Long notifId) {
        Notification notification = notificationService.findNotificationById(notifId);
        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            notificationService.updateStatusRead(notifId);
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FETCH_ERROR_MESSAGE);
        }
    }

    /**
     * Marks all notifications as read for the current user.
     *
     * @return boolean indicating whether the operation was successful.
     */
    @GetMapping("/markAllRead")
    public boolean markAllRead() {
        AuthenticationResponse response = notificationService.responseAuthentication();
        return notificationService.markAsRead(response.getUserId());
    }

    /**
     * Checks if a notification with the provided data exists.
     *
     * @param checkNotificationExistsDTO Data transfer object containing the notification data to check.
     * @return boolean indicating whether the notification data exists.
     */
    @PostMapping("/checkData")
    public boolean checkExists(@RequestBody CheckNotificationExistsDTO checkNotificationExistsDTO) {
        return notificationService.checkDataExists(checkNotificationExistsDTO);
    }
}
