package com.scaffold.template.services;

import com.scaffold.template.dtos.EmployeeDto;
import com.scaffold.template.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    Employee getEmployee(Long id);
    List<Employee> getEmployeeList();
    Employee createEmployee(EmployeeDto employeeDto);
    Employee updateEmployee(EmployeeDto employeeDto, Boolean changeTime);
    boolean deleteEmployee(Long employeeId);
    Page<EmployeeDto> getEmployeesPaged(int page, int size, String search);
}
