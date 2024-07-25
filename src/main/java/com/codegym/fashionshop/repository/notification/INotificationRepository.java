package com.codegym.fashionshop.repository.notification;

import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.entities.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link Notification} entities.
 * Provides methods for performing CRUD operations and custom queries related to notifications.
 * <p>
 * This repository uses native SQL queries for operations that are not easily expressed using JPA methods.
 * </p>
 *
 * <p>
 * All modifying operations are transactional.
 * </p>
 *
 * @author NhiNTY
 */
@Transactional
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retrieves a list of notifications for a given role and user.
     *
     * @param roleId the ID of the role to filter notifications.
     * @param userId the ID of the user to filter notifications.
     * @return a list of {@link INotificationDTO} objects representing the notifications.
     */
    @Query(value = """
            SELECT n.notif_id, n.topic, n.create_date, n.content, u.status_read 
            FROM notification n
            JOIN user_notification u ON n.notif_id = u.notif_id
            JOIN app_user a ON a.user_id = u.user_id
            WHERE a.role_id = :roleId AND u.user_id = :userId 
            ORDER BY n.create_date DESC
            """, nativeQuery = true)
    List<INotificationDTO> findAll(@Param("roleId") Long roleId, @Param("userId") Long userId);

    /**
     * Inserts a new notification into the database.
     *
     * @param content    the content of the notification.
     * @param createDate the creation date of the notification.
     * @param topic      the topic of the notification.
     * @return the number of rows affected by the insert operation.
     */
    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO notification (content, create_date, topic) 
            VALUES (:content, :createDate, :topic)
            """, nativeQuery = true)
    int createNotification(@Param("content") String content, @Param("createDate") LocalDateTime createDate, @Param("topic") String topic);

    /**
     * Inserts new user notification entries for each user associated with the given role IDs.
     *
     * @param notifId   the ID of the notification to be associated with users.
     * @param listRole  a list of role IDs used to filter users for notification association.
     */
    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO user_notification (status_read, user_id, notif_id)
            SELECT 0, a.user_id, :notifId
            FROM app_user a
            JOIN app_role r ON a.role_id = r.role_id
            WHERE r.role_id IN :listRole
            """, nativeQuery = true)
    void addNewNotification(@Param("notifId") Long notifId, @Param("listRole") List<Long> listRole);

    /**
     * Retrieves the ID of the most recently inserted notification.
     *
     * @return the ID of the last inserted notification.
     */
    @Query(value = """
            SELECT LAST_INSERT_ID()
            """, nativeQuery = true)
    Long getLastInsertNotificationId();

    /**
     * Retrieves a notification by its ID.
     *
     * @param notifId the ID of the notification to retrieve.
     * @return the {@link Notification} entity with the specified ID.
     */
    @Query(value = """
            SELECT * FROM notification n WHERE n.notif_id = :notifId
            """, nativeQuery = true)
    Notification findNotificationByNotifId(@Param("notifId") Long notifId);

    /**
     * Retrieves a list of notifications filtered by read status for a specific user.
     *
     * @param userId    the ID of the user whose notifications are to be retrieved.
     * @param statusRead the read status of the notifications to retrieve.
     * @return a list of {@link INotificationDTO} objects representing the notifications.
     */
    @Query(value = """
            SELECT n.notif_id, n.content, n.create_date, n.topic, u.status_read 
            FROM notification n
            JOIN user_notification u ON n.notif_id = u.notif_id
            JOIN app_user a ON a.user_id = u.user_id
            WHERE u.status_read = :statusRead AND a.user_id = :userId 
            ORDER BY n.create_date DESC
            """, nativeQuery = true)
    List<INotificationDTO> findNotificationsByStatusRead(@Param("userId") Long userId, @Param("statusRead") boolean statusRead);

    /**
     * Marks all notifications as read for a specific user.
     *
     * @param userId the ID of the user for whom notifications are to be marked as read.
     * @return the number of notifications updated to read.
     */
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE user_notification u 
            SET u.status_read = 1 
            WHERE u.status_read = 0 AND u.user_id = :userId
            """, nativeQuery = true)
    int markAsRead(@Param("userId") Long userId);

    /**
     * Updates the read status of a specific notification for a specific user.
     *
     * @param userId  the ID of the user.
     * @param notifId the ID of the notification to update.
     */
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE user_notification u 
            SET u.status_read = 1 
            WHERE u.user_id = :userId AND u.status_read = 0 AND u.notif_id = :notifId
            """, nativeQuery = true)
    void updateStatusRead(@Param("userId") Long userId, @Param("notifId") Long notifId);

    /**
     * Checks if a notification with the specified topic, content, and roles exists.
     *
     * @param topic     the topic of the notification.
     * @param content   the content of the notification.
     * @param listRole  a list of role IDs to check for notification existence.
     * @return the number of notifications that match the criteria.
     */
    @Query(value = """
            SELECT COUNT(n.notif_id)
            FROM notification n
            JOIN user_notification u ON u.notif_id = n.notif_id
            JOIN app_user a ON a.user_id = u.user_id
            WHERE n.topic = :topic
            AND n.content = :content
            AND a.role_id IN :listRole
            """, nativeQuery = true)
    int checkDataExists(@Param("topic") String topic, @Param("content") String content, @Param("listRole") List<Long> listRole);
}
