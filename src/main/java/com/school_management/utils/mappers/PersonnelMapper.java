package com.school_management.utils.mappers;

import com.school_management.models.dto.PersonnelDTO;
import com.school_management.models.entities.PersonnelEntity;

public class PersonnelMapper {

    public static PersonnelDTO toDTO(PersonnelEntity entity) {
        if (entity == null) return null;

        PersonnelDTO dto = new PersonnelDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setDateNaissance(entity.getDateNaissance());
        dto.setTelephone(entity.getTelephone());
        dto.setEmail(entity.getEmail());
        dto.setPoste(entity.getPoste());
        dto.setDateEmbauche(entity.getDateEmbauche());
        dto.setSalaire(entity.getSalaire());
        dto.setMatieres(entity.getMatieres());

        return dto;
    }

    public static PersonnelEntity toEntity(PersonnelDTO dto) {
        if (dto == null) return null;

        PersonnelEntity entity = new PersonnelEntity(
                dto.getNom(),
                dto.getPrenom(),
                dto.getDateNaissance(),
                dto.getTelephone(),
                dto.getEmail(),
                dto.getAdresse()
        );

        entity.setId(dto.getId());
        entity.setPoste(dto.getPoste());
        entity.setDateEmbauche(dto.getDateEmbauche());
        entity.setSalaire(dto.getSalaire());
        entity.setMatieres(dto.getMatieres());

        return entity;
    }
}
