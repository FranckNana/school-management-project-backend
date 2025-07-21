package com.school_management.repository;

import com.school_management.models.entities.ScheduleEntity;
import com.school_management.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
