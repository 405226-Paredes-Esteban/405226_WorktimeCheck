import { Area } from "./area";
import { EmployeeShift } from "./employeeShift";

export interface Employee{
    employeeId:number,
    employeeName:string,
    employeeLastName:string,
    employeeDocument:string,
    employeeArea:Area,
    employeeState:number,
    employeeShifts:EmployeeShift[]
}