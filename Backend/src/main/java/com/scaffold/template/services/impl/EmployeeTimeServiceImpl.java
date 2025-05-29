package com.scaffold.template.services.impl;

import com.scaffold.template.entities.EmployeeEntity;
import com.scaffold.template.entities.EmployeeTimeEntity;
import com.scaffold.template.models.EmployeeTime;
import com.scaffold.template.repositories.EmployeeTimeRepository;
import com.scaffold.template.services.AreaService;
import com.scaffold.template.services.EmployeeTimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeTimeServiceImpl implements EmployeeTimeService {

    @Autowired
    private EmployeeTimeRepository timeRepository;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<EmployeeTime> getEmployeeTimesByEmployee(int page, int size, Long employeeId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeTimeEntity> timePage;

        timePage = timeRepository.searchByEmployeeId(employeeId, pageable);

        return timePage.map(employeeTimeEntity -> modelMapper.map(employeeTimeEntity, EmployeeTime.class));
    }

    @Override
    public EmployeeTime updateEmployeeTime(EmployeeTime employeeTime) {
        return null;
    }

    @Override
    public boolean deleteEmployeeTime(Long timeId) {
        boolean auxReturn = false;
        Optional<EmployeeTimeEntity> timeEntity = timeRepository.findById(timeId);
        if (timeEntity.isPresent()){
            timeEntity.get().setTimeState(0L);
            timeRepository.save(timeEntity.get());
            auxReturn = true;
        }
        return auxReturn;
    }

    @Override
    public EmployeeTime getEmployeeTimeById(Long id) {
        Optional<EmployeeTimeEntity> timeEntity = timeRepository.findById(id);
        if (timeEntity.isPresent()){
            return modelMapper.map(timeEntity,EmployeeTime.class);
        }
        return null;
    }
}
