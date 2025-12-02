package com.school_management.utils.mappers;

import com.school_management.models.dto.StudentDTO;
import com.school_management.models.entities.StudentEntity;

import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentDTO toDto(StudentEntity entity) {
        if (entity == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setNumeroMatricule(entity.getNumeroMatricule());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setDateNaissance(entity.getDateNaissance());
        dto.setClasse(entity.getClasse());
        dto.setAdresse(entity.getAdresse());
        dto.setEmail(entity.getEmail());
        dto.setTelephone(entity.getTelephone());
        dto.setNomParent(entity.getNomParent());
        dto.setTelephoneParent(entity.getTelephoneParent());

        if(dto.getNotes() != null) {
            dto.setNotes(entity.getNotes().stream().map(NoteMapper::toDto).collect(Collectors.toList()));
        }
        if(dto.getPresences() != null) {
            dto.setPresences(entity.getPresences().stream().map(PresenceMapper::toDto).collect(Collectors.toList()));
        }
        if(dto.getPaiements() != null) {
            dto.setPaiements(entity.getPaiements().stream().map(PaiementMapper::toDto).collect(Collectors.toList()));
        }

        dto.setPrixScholarite(entity.getPrixScholarite());
        dto.setResteApayer(entity.getResteApayer());
        dto.setAnneeScolaire(entity.getAnneeScolaire());

        return dto;
    }

    public static StudentEntity toEntity(StudentDTO dto) {
        if (dto == null) return null;

        StudentEntity entity = new StudentEntity();
        entity.setId(dto.getId());
        entity.setNumeroMatricule(dto.getNumeroMatricule());
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setClasse(dto.getClasse());
        entity.setAdresse(dto.getAdresse());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setNomParent(dto.getNomParent());
        entity.setTelephoneParent(dto.getTelephoneParent());

        // On mappe sans relier immédiatement les enfants au parent
        // lier les enfants à leur parent
        if(dto.getNotes() != null) {
            entity.setNotes(dto.getNotes().stream().map(NoteMapper::toEntity).collect(Collectors.toList()));
            entity.getNotes().forEach(n -> n.setStudent(entity));
        }
        if(dto.getPresences() != null) {
            entity.setPresences(dto.getPresences().stream().map(PresenceMapper::toEntity).collect(Collectors.toList()));
            entity.getPresences().forEach(p -> p.setStudent(entity));
        }

        entity.setPrixScholarite(dto.getPrixScholarite());
        entity.setResteApayer(dto.getResteApayer());
        entity.setAnneeScolaire(dto.getAnneeScolaire());
        return entity;
    }
}
