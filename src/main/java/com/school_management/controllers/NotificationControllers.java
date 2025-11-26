package com.school_management.controllers;

import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.NotificationDTO;
import com.school_management.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NotificationControllers extends ControllerExceptionHandler {

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getAll() {
        List<NotificationDTO> notifications = this.notificationService.getAll();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        NotificationDTO newNotif = this.notificationService.create(notificationDTO);
        return new ResponseEntity<>(newNotif, HttpStatus.OK);
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        this.notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
