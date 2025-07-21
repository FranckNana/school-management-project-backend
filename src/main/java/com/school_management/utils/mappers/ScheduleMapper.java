package com.school_management.utils.mappers;

import com.school_management.models.dto.ScheduleDTO;
import com.school_management.models.entities.ScheduleEntity;

public class ScheduleMapper {

    public static ScheduleDTO toDTO(ScheduleEntity entity) {
        if (entity == null) return null;

        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(entity.getId());
        dto.setClasse(entity.getClasse());
        dto.setJour(entity.getJour());
        dto.setHeureDebut(entity.getHeureDebut());
        dto.setHeureFin(entity.getHeureFin());
        dto.setMatiere(entity.getMatiere());
        dto.setEnseignant(entity.getEnseignant());
        dto.setSalle(entity.getSalle());

        return dto;
    }

    public static ScheduleEntity toEntity(ScheduleDTO dto) {
        if (dto == null) return null;

        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(dto.getId());
        entity.setClasse(dto.getClasse());
        entity.setJour(dto.getJour());
        entity.setHeureDebut(dto.getHeureDebut());
        entity.setHeureFin(dto.getHeureFin());
        entity.setMatiere(dto.getMatiere());
        entity.setEnseignant(dto.getEnseignant());
        entity.setSalle(dto.getSalle());

        return entity;
    }
}
