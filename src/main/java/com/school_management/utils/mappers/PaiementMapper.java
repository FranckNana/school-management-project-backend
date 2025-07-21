package com.school_management.utils.mappers;

import com.school_management.models.dto.PaiementDTO;
import com.school_management.models.entities.PaiementEntity;
import com.school_management.models.entities.StudentEntity;

import java.util.stream.Collectors;

public class PaiementMapper {

    public static PaiementDTO toDto(PaiementEntity entity) {
        if (entity == null) return null;

        return new PaiementDTO(
                entity.getId(),
                entity.getDate(),
                entity.getMontant(),
                entity.getMotif(),
                entity.getEleve().getId()
        );

    }

    public static PaiementEntity toEntity(PaiementDTO dto, StudentEntity eleve) {
        if (dto == null) return null;
        PaiementEntity entity = new PaiementEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setMontant(dto.getMontant());
        entity.setMotif(dto.getMotif());
        entity.setEleve(eleve);
        return entity;
    }
}
