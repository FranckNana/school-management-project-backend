package com.school_management.controllers;


import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.MetricsDTO;
import com.school_management.models.dto.NotificationDTO;
import com.school_management.services.IMetricsService;
import com.school_management.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MetricsControllers extends ControllerExceptionHandler {

    @Autowired
    private IMetricsService metricsService;

    @GetMapping("/metrics")
    public ResponseEntity<MetricsDTO> getMetrics() {
        MetricsDTO metrics = this.metricsService.getAllMetrics();
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}
