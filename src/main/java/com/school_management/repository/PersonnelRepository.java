package com.school_management.repository;

import com.school_management.models.entities.PersonnelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelRepository extends JpaRepository<PersonnelEntity, Long> {
}
