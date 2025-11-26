package com.school_management.repository;

import com.school_management.models.entities.PersonnelEntity;
import com.school_management.models.enums.Poste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<PersonnelEntity, Long> {
    List<PersonnelEntity> findByPoste(Poste poste);
}
