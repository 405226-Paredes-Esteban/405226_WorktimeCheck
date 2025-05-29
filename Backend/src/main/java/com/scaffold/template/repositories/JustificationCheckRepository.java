package com.scaffold.template.repositories;

import com.scaffold.template.entities.JustificationCheckEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificationCheckRepository extends JpaRepository<JustificationCheckEntity, Long> {
    Page<JustificationCheckEntity> searchPaged(Pageable pageable);
}
