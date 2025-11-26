package com.school_management.models.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long id;
    private String icon;
    private String color;
    private String importance;
    private String title;
    private String description;
    private LocalDateTime dateTime;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, String icon, String color, String importance, String title, String description, LocalDateTime dateTime) {
        this.id = id;
        this.icon = icon;
        this.color = color;
        this.importance = importance;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
