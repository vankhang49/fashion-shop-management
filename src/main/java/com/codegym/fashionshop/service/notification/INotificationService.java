package com.codegym.fashionshop.service.notification;

import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.Notification;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing notifications.
 * Provides methods for CRUD operations and other notification-related functionalities.
 * <p>
 * Implementations of this interface should handle the business logic for managing notifications
 * and interact with the data access layer.
 * </p>
 *
 * @author NhiNTY
 */
public interface INotificationService {

    /**
     * Retrieves all notifications based on the provided role and user IDs.
     *
     * @param roleId The ID of the role to filter notifications.
     * @param userId The ID of the user to filter notifications.
     * @return A list of {@link INotificationDTO} objects representing the notifications.
     */
    List<INotificationDTO> getAllNotification(Long roleId, Long userId);

    /**
     * Adds a new notification with the specified details.
     *
     * @param content    The content of the notification.
     * @param createDate The creation date and time of the notification.
     * @param topic      The topic or subject of the notification.
     * @param listRole   A list of role IDs to which the notification should be sent.
     */
    void addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole);

    /**
     * Finds a notification by its ID.
     *
     * @param notifId The ID of the notification to find.
     * @return The {@link Notification} object if found, or null otherwise.
     */
    Notification findNotificationById(Long notifId);

    /**
     * Finds notifications filtered by the read status for a specific user.
     *
     * @param userId     The ID of the user to filter notifications.
     * @param statusRead The read status of the notifications (true for read, false for unread).
     * @return A list of {@link INotificationDTO} objects matching the specified criteria.
     */
    List<INotificationDTO> findNotificationsByStatusRead(Long userId, boolean statusRead);

    /**
     * Marks all notifications as read for a specific user.
     *
     * @param userId The ID of the user for whom notifications should be marked as read.
     * @return true if the notifications were successfully marked as read, false otherwise.
     */
    boolean markAsRead(Long userId);

    /**
     * Marks all notifications as read for all users.
     *
     * @return true if all notifications were successfully marked as read, false otherwise.
     */
    boolean isMarkAllAsRead();

    /**
     * Updates the read status of a specific notification for all users.
     *
     * @param notifId The ID of the notification to update.
     */
    void updateStatusRead(Long notifId);

    /**
     * Checks if a notification with the specified data exists.
     *
     * @param checkNotificationExistsDTO The DTO containing data to check for existence.
     * @return true if a matching notification exists, false otherwise.
     */
    boolean checkDataExists(CheckNotificationExistsDTO checkNotificationExistsDTO);

    /**
     * Retrieves authentication response for the service.
     *
     * @return An {@link AuthenticationResponse} object representing the authentication response.
     */
    AuthenticationResponse responseAuthentication();
}
