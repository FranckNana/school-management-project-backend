package com.school_management.repository;

import com.school_management.models.entities.BulletinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BulletinRepository extends JpaRepository<BulletinEntity, Long> {
    Optional<List<BulletinEntity>> findByEleveId(Long eleveId);
}
