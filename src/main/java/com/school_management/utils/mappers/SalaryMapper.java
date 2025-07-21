package com.school_management.utils.mappers;

import com.school_management.models.dto.SalaryDTO;
import com.school_management.models.entities.SalaryEntity;

public class SalaryMapper {

    public static SalaryDTO toDto(SalaryEntity entity) {
        if (entity == null) return null;

        SalaryDTO dto = new SalaryDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setEmploye(entity.getEmploye());
        dto.setMontant(entity.getMontant());
        dto.setMois(entity.getMois());
        dto.setPoste(entity.getPoste());
        return dto;
    }

    public static SalaryEntity toEntity(SalaryDTO dto) {
        if (dto == null) return null;

        SalaryEntity entity = new SalaryEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setEmploye(dto.getEmploye());
        entity.setMontant(dto.getMontant());
        entity.setMois(dto.getMois());
        entity.setPoste(dto.getPoste());
        return entity;
    }
}
