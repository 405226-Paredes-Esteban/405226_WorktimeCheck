package com.scaffold.template.repositories;

import com.scaffold.template.dtos.EmployeeJustificationDto;
import com.scaffold.template.entities.TimeJustificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeJustificationRepository extends JpaRepository<TimeJustificationEntity, Long> {
    @Query("""
    SELECT tj FROM TimeJustificationEntity tj
    JOIN tj.time t
    WHERE t.employeeId = :employeeId
""")
    Page<TimeJustificationEntity> findByEmployeeId(@Param("employeeId") Long employeeId, Pageable pageable);
}
