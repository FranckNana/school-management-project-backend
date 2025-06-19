package com.school_management.repository;

import com.school_management.models.entities.PersonnelEntity;
import com.school_management.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    long countByNumeroMatriculeStartingWith(String prefix);
}
