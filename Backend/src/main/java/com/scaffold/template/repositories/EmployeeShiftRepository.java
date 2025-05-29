package com.scaffold.template.repositories;

import com.scaffold.template.entities.EmployeeShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeShiftRepository extends JpaRepository<EmployeeShiftEntity,Long> {
    List<EmployeeShiftEntity> findAllByEmployeeId(Long id);
    Optional<EmployeeShiftEntity> findByEmployeeIdAndShiftDay(Long employeeId, String shiftDay);
}
