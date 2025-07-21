package com.school_management.services;

import com.school_management.models.dto.ScheduleDTO;

import java.util.List;

public interface IScheduleService {
    public List<ScheduleDTO> getAll();
    public ScheduleDTO getById(Long id);
    public ScheduleDTO create(ScheduleDTO scheduleDTO);
    public ScheduleDTO update(ScheduleDTO scheduleDTO);
    public void delete(Long id);
}
