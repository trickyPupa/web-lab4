import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Point } from '../models/point.model';
import {AuthService} from "./auth.service";

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
    return this.http.post<any>(
        `${this.API_URL}/point`,
        point,
        { headers: this.authService.getAuthHeaders() }
    ).pipe(
        map(response => ({
          x: response.data.x,
          y: response.data.y,
          r: response.data.r,
          result: response.data.result
        }))
    );
  }

  getPoints(): Observable<Point[]> {
    return this.http.get<any>(
        `${this.API_URL}/point`,
        { headers: this.authService.getAuthHeaders() }
    ).pipe(
        map(response => response.data.map((point: any) => ({
          x: point.x,
          y: point.y,
          r: point.r,
          result: point.result
        })))
    );
  }

    deletePoints(): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/point`, {
            headers: this.authService.getAuthHeaders()
        });
    }
}