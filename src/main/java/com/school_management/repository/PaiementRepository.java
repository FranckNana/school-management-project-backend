package com.school_management.repository;

import com.school_management.models.entities.PaiementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<PaiementEntity, Long> {
}
