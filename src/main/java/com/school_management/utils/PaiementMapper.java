package com.school_management.utils;

import com.school_management.models.dto.PaiementDTO;
import com.school_management.models.entities.PaiementEntity;

public class PaiementMapper {

    public static PaiementDTO toDto(PaiementEntity entity) {
        if (entity == null) return null;
        return new PaiementDTO(
                entity.getId(),
                entity.getDate(),
                entity.getMontant(),
                entity.getMotif()
        );
    }

    public static PaiementEntity toEntity(PaiementDTO dto) {
        if (dto == null) return null;
        PaiementEntity entity = new PaiementEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setMontant(dto.getMontant());
        entity.setMotif(dto.getMotif());
        return entity;
    }
}
