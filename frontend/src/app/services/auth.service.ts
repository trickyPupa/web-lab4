import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = '/api';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
    this.isAuthenticatedSubject.next(!!localStorage.getItem('token'));
  }

  getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    });
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.API_URL}/auth/login`,
        { username, password },
        this.httpOptions
    ).pipe(
        tap((response: any) => {
            if (response?.data?.token) {
                localStorage.setItem('token', response.data.token);
                this.isAuthenticatedSubject.next(true);
            } else {
                throw new Error('Token not received');
            }
        })
    );
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post(`${this.API_URL}/auth/register`,
        { username, password },
        this.httpOptions
    ).pipe(
        tap((response: any) => {
          if (response.success == true) {
            this.isAuthenticatedSubject.next(true);
          }
          else {
              throw new Error(response.data.message);
          }
        })
    );
  }

  logout(): void {
    this.http.post<any>(
      `${this.API_URL}/auth/logout`,
      { headers: this.getAuthHeaders() }
    );
    localStorage.removeItem('token');
    this.isAuthenticatedSubject.next(false);
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  getAuthState(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }
}