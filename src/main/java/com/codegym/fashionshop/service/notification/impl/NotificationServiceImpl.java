package com.codegym.fashionshop.service.notification.impl;

import com.codegym.fashionshop.dto.CheckNotificationExistsDTO;
import com.codegym.fashionshop.dto.INotificationDTO;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.Notification;
import com.codegym.fashionshop.repository.notification.INotificationRepository;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import com.codegym.fashionshop.service.notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@link INotificationService} for managing notifications.
 * This class interacts with the repository layer to perform CRUD operations on notifications.
 *
 * @author NhiNTY
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<INotificationDTO> getAllNotification(Long roleId, Long userId) {
        return notificationRepository.findAll(roleId, userId);
    }

    @Override
    public void addNotification(String content, LocalDateTime createDate, String topic, List<Long> listRole) {
        int createInsert = notificationRepository.createNotification(content, createDate, topic);
        if (createInsert > 0) {
            Long lastInsert = notificationRepository.getLastInsertNotificationId();
            notificationRepository.addNewNotification(lastInsert, listRole);
        }
    }

    @Override
    public Notification findNotificationById(Long notifId) {
        return notificationRepository.findNotificationByNotifId(notifId);
    }

    @Override
    public List<INotificationDTO> findNotificationsByStatusRead(Long userId, boolean statusRead) {
        return notificationRepository.findNotificationsByStatusRead(userId, statusRead);
    }

    @Override
    public boolean markAsRead(Long userId) {
        if (!isMarkAllAsRead()) {
            int updateRows = notificationRepository.markAsRead(userId);
            return updateRows > 0;
        }
        return false;
    }

    @Override
    public boolean isMarkAllAsRead() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        List<INotificationDTO> list = notificationRepository.findNotificationsByStatusRead(response.getUserId(), false);
        return list == null;
    }

    @Override
    public void updateStatusRead(Long notifId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        notificationRepository.updateStatusRead(response.getUserId(), notifId);
    }

    @Override
    public boolean checkDataExists(CheckNotificationExistsDTO checkNotificationExistsDTO) {
        int listSize = notificationRepository.checkDataExists(
                checkNotificationExistsDTO.getTopic(),
                checkNotificationExistsDTO.getContent(),
                checkNotificationExistsDTO.getListRole());
        return listSize > 0;
    }

    @Override
    public AuthenticationResponse responseAuthentication() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return authenticationService.getMyInfo(username);
        } catch (Exception e) {
            return new AuthenticationResponse();
        }
    }
}
