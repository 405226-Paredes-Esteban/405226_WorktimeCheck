package com.scaffold.template.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeShift {
    private Long shiftId;

    private Long employeeId;

    private String shiftDay;

    private LocalTime shiftEntry;

    private LocalTime shiftExit;

    private Long shiftDuration;

    private Long shiftState;

    private Long shiftAudUser;
}
