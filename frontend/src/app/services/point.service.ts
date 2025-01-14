import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Point } from '../models/point.model';

@Injectable({
  providedIn: 'root'
})
export class PointService {
  private readonly API_URL = '/api';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  checkPoint(point: Point): Observable<Point> {
    return this.http.post<Point>(`${this.API_URL}/point`,
        point,
        this.httpOptions);
  }

  getPoints(): Observable<Point[]> {
    return this.http.get<Point[]>(`${this.API_URL}/point`);
  }
}