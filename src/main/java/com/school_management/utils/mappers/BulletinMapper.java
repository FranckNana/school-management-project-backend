package com.school_management.utils.mappers;

import com.school_management.models.dto.BulletinDTO;
import com.school_management.models.dto.NoteDTO;
import com.school_management.models.entities.BulletinEntity;
import com.school_management.models.entities.NoteEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BulletinMapper {

    public static BulletinDTO toDto(BulletinEntity entity) {
        if (entity == null) return null;

        BulletinDTO dto = new BulletinDTO();
        dto.setId(entity.getId());
        dto.setEleveId(entity.getEleveId());
        dto.setNomEleve(entity.getNomEleve());
        dto.setClasse(entity.getClasse());
        dto.setTrimestre(entity.getTrimestre());
        dto.setAnnee(entity.getAnnee());
        dto.setMoyenneGenerale(entity.getMoyenneGenerale());
        dto.setRang(entity.getRang());
        dto.setAppreciation(entity.getAppreciation());
        dto.setDateGeneration(entity.getDateGeneration());

        List<NoteDTO> notes = entity.getNotes() != null
                ? entity.getNotes().stream().map(NoteMapper::toDto).collect(Collectors.toList())
                : null;
        dto.setNotes(notes);

        return dto;
    }

    public static BulletinEntity toEntity(BulletinDTO dto) {
        if (dto == null) return null;

        BulletinEntity entity = new BulletinEntity();
        entity.setId(dto.getId());
        entity.setEleveId(dto.getEleveId());
        entity.setNomEleve(dto.getNomEleve());
        entity.setClasse(dto.getClasse());
        entity.setTrimestre(dto.getTrimestre());
        entity.setAnnee(dto.getAnnee());
        entity.setMoyenneGenerale(dto.getMoyenneGenerale());
        entity.setRang(dto.getRang());
        entity.setAppreciation(dto.getAppreciation());
        entity.setDateGeneration(dto.getDateGeneration());

        List<NoteEntity> notes = dto.getNotes() != null
                ? dto.getNotes().stream().map(noteDto -> NoteMapper.toEntity(noteDto))
                                .collect(Collectors.toList())
                : null;
        entity.setNotes(notes);

        return entity;
    }
}
