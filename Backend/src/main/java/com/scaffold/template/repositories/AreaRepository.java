package com.scaffold.template.repositories;

import com.scaffold.template.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {
    Optional<AreaEntity> findByDescription(String description);
    Optional<AreaEntity> findByAreaResponsible(Long responsibleId);
}
