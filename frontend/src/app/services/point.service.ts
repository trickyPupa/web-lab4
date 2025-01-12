import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Point } from '../models/point.model';

@Injectable({
  providedIn: 'root'
})
export class PointService {
  private readonly API_URL = 'http://your-backend-url/api';

  constructor(private http: HttpClient) {}

  checkPoint(point: Point): Observable<Point> {
    return this.http.post<Point>(`${this.API_URL}/points/check`, point);
  }

  getPoints(): Observable<Point[]> {
    return this.http.get<Point[]>(`${this.API_URL}/points`);
  }
}