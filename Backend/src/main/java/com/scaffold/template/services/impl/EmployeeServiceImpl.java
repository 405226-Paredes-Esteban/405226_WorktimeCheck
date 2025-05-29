package com.scaffold.template.services.impl;

import com.scaffold.template.dtos.EmployeeDto;
import com.scaffold.template.dtos.EmployeeShiftDto;
import com.scaffold.template.entities.AreaEntity;
import com.scaffold.template.entities.EmployeeEntity;
import com.scaffold.template.models.Employee;
import com.scaffold.template.models.EmployeeShift;
import com.scaffold.template.repositories.EmployeeRepository;
import com.scaffold.template.services.EmployeeService;
import com.scaffold.template.services.EmployeeShiftService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeShiftService shiftService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Employee getEmployee(Long id) {
        Optional<EmployeeEntity> entity = employeeRepository.findById(id);
        if (entity.isPresent()) {
            Employee employee = modelMapper.map(entity.get(),Employee.class);
            List<EmployeeShift> shiftEntities = shiftService.getEmployeeShiftsByEmployee(employee.getEmployeeId());
            employee.setEmployeeShifts(shiftEntities);
            return employee;
        }
        return null;
    }

    @Override
    public List<Employee> getEmployeeList() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> auxReturn = new ArrayList<Employee>();
        for (EmployeeEntity e : employeeEntities){
            auxReturn.add(modelMapper.map(e,Employee.class));
        }
        return auxReturn;
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity auxEntity = (modelMapper.map(employeeDto,EmployeeEntity.class));
        auxEntity.setAuditUser(1L);
        auxEntity.setEmployeeState(1L);
        auxEntity.setEmployeeArea(new AreaEntity(1L,"C",1L,1L,1L));
        auxEntity = employeeRepository.save(auxEntity);
        for (EmployeeShiftDto s : employeeDto.getEmployeeShifts()){
            s.setEmployeeId(auxEntity.getEmployeeId());
            shiftService.createShift(modelMapper.map(s, EmployeeShift.class));
        }
        return modelMapper.map(auxEntity,Employee.class);
    }

    @Override
    public Employee updateEmployee(EmployeeDto employeeDto, Boolean changeTime) {
        Optional<EmployeeEntity> entity = employeeRepository.findById(employeeDto.getEmployeeId());
        if (entity.isPresent()) {
            EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
            employeeEntity.setEmployeeId(employeeDto.getEmployeeId());
            employeeEntity.setAuditUser(1L);
            employeeEntity.setEmployeeState(1L);
            employeeRepository.save(employeeEntity);
            if (changeTime) {
                shiftService.deleteShiftsByEmployee(entity.get().getEmployeeId());
                for (EmployeeShiftDto s : employeeDto.getEmployeeShifts()) {
                    shiftService.createShift(modelMapper.map(s, EmployeeShift.class));
                }
            }
        }
        return modelMapper.map(entity.get(), Employee.class);
    }

    @Override
    public boolean deleteEmployee(Long employeeId) {
        boolean auxReturn = false;
        Optional<EmployeeEntity> entity = employeeRepository.findById(employeeId);
        if (entity.isPresent()) {
            EmployeeEntity auxEntity = entity.get();
            auxEntity.setEmployeeState(0L);
            employeeRepository.save(auxEntity);
            auxReturn = true;
        }
        return auxReturn;
    }

    @Override
    public Page<EmployeeDto> getEmployeesPaged(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<EmployeeEntity> employeePage;

        if (search == null || search.isBlank()) {
            employeePage = employeeRepository.findAll(pageable);
        } else {
            employeePage = employeeRepository.searchByName(search.toLowerCase(), pageable);
        }

        return employeePage.map(entity -> modelMapper.map(entity, EmployeeDto.class));
    }

}
