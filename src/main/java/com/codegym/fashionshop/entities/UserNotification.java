package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_notification")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean status_read;

    @ManyToOne
    @JoinColumn(name = "notif_id")
    private Notification INotificationDTO;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

}