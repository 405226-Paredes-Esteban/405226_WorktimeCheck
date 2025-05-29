import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../services/User/user.service';
import { UserDto } from '../../../models/userDto';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class RegisterComponent {
  private router = inject(Router);
  private userService:UserService = inject(UserService);

  user: UserDto = {
    userName: '',
    email: '',
    password: ''
  };

  onSubmit() {
    this.userService.register(this.user).subscribe({
      next: () => {
        alert('Registro exitoso');
        this.router.navigate(['login']);
      },
      error: () => {
        alert('Error en el registro');
      }
    });
  }

  redirectLogin() {
    this.router.navigate(['login']);
  }
}
