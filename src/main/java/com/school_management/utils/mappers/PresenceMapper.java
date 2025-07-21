package com.school_management.utils.mappers;

import com.school_management.models.dto.PresenceDTO;
import com.school_management.models.entities.PresenceEntity;

public class PresenceMapper {

    public static PresenceDTO toDto(PresenceEntity entity) {
        if (entity == null) return null;
        return new PresenceDTO(
                entity.getId(),
                entity.getDate(),
                entity.isPresent(),
                entity.getMotif()
        );
    }

    public static PresenceEntity toEntity(PresenceDTO dto) {
        if (dto == null) return null;
        PresenceEntity entity = new PresenceEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setPresent(dto.isPresent());
        entity.setMotif(dto.getMotif());
        return entity;
    }
}
