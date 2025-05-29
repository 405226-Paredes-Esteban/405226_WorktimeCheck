package com.scaffold.template.repositories;

import com.scaffold.template.entities.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    @Query("""
    SELECT e FROM EmployeeEntity e 
    WHERE LOWER(e.employeeName) LIKE %:search% 
""")
    Page<EmployeeEntity> searchByName(@Param("search") String search, Pageable pageable);

}
