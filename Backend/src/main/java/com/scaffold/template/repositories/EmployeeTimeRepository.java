package com.scaffold.template.repositories;

import com.scaffold.template.entities.EmployeeTimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTimeRepository extends JpaRepository<EmployeeTimeEntity, Long> {
    List<EmployeeTimeEntity> findByTimeTypeAndEmployeeId(char timeType, Long employeeId);

    Page<EmployeeTimeEntity> searchByEmployeeId(Long employeeId, Pageable pageable);


}
