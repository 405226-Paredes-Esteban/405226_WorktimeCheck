package com.scaffold.template.services;

import com.scaffold.template.models.EmployeeShift;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeShiftService {
    List<EmployeeShift> getEmployeeShiftsByEmployee(Long employeeId);
    EmployeeShift createShift(EmployeeShift shift);
    EmployeeShift updateShift(EmployeeShift shift);
    boolean deleteShift(Long shiftId);
    boolean deleteShiftsByEmployee(Long employeeId);
}
