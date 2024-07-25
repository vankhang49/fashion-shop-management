package com.codegym.fashionshop.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public interface INotificationDTO {
    Long getNotifId();
    String getTopic();
    String getContent();
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime getCreateDate();
    Boolean getStatusRead();

}
