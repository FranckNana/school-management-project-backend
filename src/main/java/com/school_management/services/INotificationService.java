package com.school_management.services;

import com.school_management.models.dto.NotificationDTO;

import java.util.List;

public interface INotificationService {
    public List<NotificationDTO> getAll();
    public NotificationDTO create(NotificationDTO notificationDTO);
    public void delete(Long id);
}
