import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoginMode: boolean = true;
  submitted: boolean = false;

  constructor(
      private authService: AuthService,
      private router: Router
  ) {}

  toggleMode(event: Event): void {
    event.preventDefault();
    this.isLoginMode = !this.isLoginMode;
    this.errorMessage = '';
    this.submitted = false;
  }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = '';

    if (!this.username || !this.password) {
      return;
    }

    if (this.isLoginMode) {
      this.authService.login(this.username, this.password).subscribe({
        next: () => this.router.navigate(['/main']),
        error: () => this.errorMessage = 'Invalid credentials'
      });
    } else {
      this.authService.register(this.username, this.password).subscribe({
        next: () => {
          this.isLoginMode = true;
          this.errorMessage = 'Registration successful! Please login.';
          this.username = '';
          this.password = '';
          this.submitted = false;
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Username already in use';
          // 'Registration failed. Please try again.'
        }
      });
    }
  }
}