package com.codegym.fashionshop.dto;
import java.util.List;

public class CheckNotificationExistsDTO {
    String topic;
    String content;
    List<Long> listRole;

    public CheckNotificationExistsDTO(String topic, String content, List<Long> listRole) {
        this.topic = topic;
        this.content = content;
        this.listRole = listRole;
    }

    public CheckNotificationExistsDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Long> getListRole() {
        return listRole;
    }

    public void setListRole(List<Long> listRole) {
        this.listRole = listRole;
    }
}
