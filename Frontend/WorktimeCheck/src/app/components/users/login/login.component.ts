import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../services/User/user.service';
import { AuthDto } from '../../../models/authDto';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule]
})
export class LoginComponent {
  private router = inject(Router);
  private userService = inject(UserService);

  auth: AuthDto = {
    userName: '',
    password: ''
  };

  onSubmit() {
    this.userService.login(this.auth).subscribe({
      next: (token: string) => {
        localStorage.setItem('token', token);
        alert('Login exitoso');
        this.router.navigate(['/employeeList']); // ✅ redirige al listado de empleados
      },
      error: (err) => {
        alert('Error de autenticación: ' + err.error);
        console.error(err);
      }
    });
  }
  redirectRegister() {
    this.router.navigate(['register']);
  }
}