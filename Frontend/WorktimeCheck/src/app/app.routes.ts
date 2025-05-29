import { Routes } from '@angular/router';
import { EmployeeCreateComponent } from './components/employees/employee-create/employee-create.component';
import { EmployeeListComponent } from './components/employees/employee-list/employee-list.component';
import { RegisterComponent } from './components/users/register/register.component';
import { LoginComponent } from './components/users/login/login.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';
import { UserListComponent } from './components/users/user-list/user-list.component';
import { AdminConfigurationComponent } from './components/admin/admin-configuration/admin-configuration.component';
import { TimeListComponent } from './components/times/time-list/time-list.component';
import { JustificationFormComponent } from './components/justification/justification-form/justification-form.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
    {
    path: '',
    component: MainLayoutComponent,
    children: [
        { path: 'employeeList', component: EmployeeListComponent },
        { path: "employee", component:EmployeeCreateComponent},
        { path: "employee/:id", component:EmployeeCreateComponent},
        { path: "config", component:AdminConfigurationComponent},
        { path: "users", component: UserListComponent},
        { path: "timeList/:id", component: TimeListComponent},
        { path: "justification", component: JustificationFormComponent}
    ]
  },
  { path: '**', redirectTo: 'login' }
];
