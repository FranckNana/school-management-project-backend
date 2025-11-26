package com.school_management.repository;

import com.school_management.models.entities.ScheduleEntity;
import com.school_management.models.entities.StudentEntity;
import com.school_management.models.enums.Jour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByJour(Jour jour);
}
