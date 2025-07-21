package com.school_management.utils.mappers;

import com.school_management.models.dto.NoteDTO;
import com.school_management.models.entities.NoteEntity;

public class NoteMapper {
    public static NoteDTO toDto(NoteEntity entity) {
        if (entity == null) return null;
        return new NoteDTO(
                entity.getId(),
                entity.getMatiere(),
                entity.getNote(),
                entity.getCoefficient(),
                entity.getTrimestre(),
                entity.getAppreciation()
        );
    }

    public static NoteEntity toEntity(NoteDTO dto) {
        if (dto == null) return null;
        NoteEntity entity = new NoteEntity();
        entity.setId(dto.getId());
        entity.setMatiere(dto.getMatiere());
        entity.setNote(dto.getNote());
        entity.setCoefficient(dto.getCoefficient());
        entity.setTrimestre(dto.getTrimestre());
        return entity;
    }
}
