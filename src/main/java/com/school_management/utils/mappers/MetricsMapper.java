package com.school_management.utils.mappers;

import com.school_management.models.dto.MetricsDTO;
import com.school_management.models.entities.MetricsEntity;

public class MetricsMapper {

    public static MetricsDTO toDTO(MetricsEntity entity) {
        if (entity == null) return null;

        return new MetricsDTO(
                entity.getTotalStudents(),
                entity.getTotalTeachers(),
                entity.getPaymentComplianceRate(),
                entity.getTodaysClassesCount(),
                entity.getYear(),
                entity.getMonth(),
                entity.getComparedStudent(),
                entity.getComparedTeacher()
        );
    }

    public static MetricsEntity toEntity(MetricsDTO dto) {
        if (dto == null) return null;

        return new MetricsEntity(
                dto.getTotalStudents(),
                dto.getTotalTeachers(),
                dto.getPaymentComplianceRate(),
                dto.getTodaysClassesCount(),
                dto.getYear(),
                dto.getMonth(),
                dto.getComparedStudent(),
                dto.getComparedTeacher()
        );
    }
}
