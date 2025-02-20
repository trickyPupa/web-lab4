import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() showLogout: boolean = false;
  studentInfo = {
    name: 'Знаменский Александр',
    group: 'P3223',
    variant: '4451'
  };

  constructor(
      private authService: AuthService,
      private router: Router
  ) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}