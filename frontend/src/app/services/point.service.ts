import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Point } from '../models/point.model';

@Injectable({
  providedIn: 'root'
})
export class PointService {
  private readonly API_URL = '/api';

  constructor(
      private http: HttpClient,
      private authService: AuthService
  ) {}

  checkPoint(point: Point): Observable<Point> {
    return this.http.post<Point>(
        `${this.API_URL}/points/check`,
        point,
        { headers: this.authService.getAuthHeaders() }
    );
  }

  getPoints(): Observable<Point[]> {
    return this.http.get<Point[]>(
        `${this.API_URL}/points`,
        { headers: this.authService.getAuthHeaders() }
    );
  }
}