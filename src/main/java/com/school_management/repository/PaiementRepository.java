package com.school_management.repository;

import com.school_management.models.entities.PaiementEntity;
import com.school_management.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<PaiementEntity, Long> {

}
