package com.scaffold.template.controllers;

import com.scaffold.template.dtos.EmployeeDto;
import com.scaffold.template.models.Employee;
import com.scaffold.template.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Angular default port
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper mapper;


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee != null) {
            return ResponseEntity.ok(mapper.map(employee,EmployeeDto.class));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto dto){
        //System.out(dto);
        Employee employee = employeeService.createEmployee(dto);
        return ResponseEntity.ok(mapper.map(employee,EmployeeDto.class));
    }

    @GetMapping("/paged")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EmployeeDto>> getEmployeesPaged(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String search
    ) {
        Page<EmployeeDto> employees = employeeService.getEmployeesPaged(page, size, search);
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id)
    {
        if (employeeService.deleteEmployee(id))
        {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/{changeTime}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto dto, @RequestParam Boolean changeTime){
        Employee employee = employeeService.updateEmployee(dto, changeTime);
        return ResponseEntity.ok(mapper.map(employee,EmployeeDto.class));
    }

}
