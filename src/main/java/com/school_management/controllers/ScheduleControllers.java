package com.school_management.controllers;

import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.ScheduleDTO;
import com.school_management.services.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScheduleControllers extends ControllerExceptionHandler {

    @Autowired
    private IScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleDTO> createShedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO schedule = this.scheduleService.create(scheduleDTO);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @PatchMapping("/schedules")
    public ResponseEntity<ScheduleDTO> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO schedule = this.scheduleService.update(scheduleDTO);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleDTO> getById(@PathVariable Long id) {
        ScheduleDTO scheduleDTO = this.scheduleService.getById(id);
        return new ResponseEntity<>(scheduleDTO, HttpStatus.OK);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleDTO>> getAll() {
        List<ScheduleDTO> schedule = this.scheduleService.getAll();
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        this.scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
