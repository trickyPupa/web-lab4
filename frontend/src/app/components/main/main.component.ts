import { Component, OnInit } from '@angular/core';
import { PointService } from '../../services/point.service';
import { Point } from '../../models/point.model';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  xOptions = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
  selectedX: number[] = [];
  y: number | null = null;
  rOptions = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
  selectedR: number[] = [];
  points: Point[] = [];

  constructor(private pointService: PointService) {}

  ngOnInit(): void {
    this.loadPoints();
  }

  loadPoints(): void {
    this.pointService.getPoints().subscribe(points => {
      this.points = points;
    });
  }

  checkPoint(clickedPoint?: Point): void {
    if (clickedPoint) {
      // Handle point click from graph
      this.pointService.checkPoint(clickedPoint).subscribe(result => {
        this.points = [...this.points, result];
      });
    } else if (this.selectedX.length && this.y !== null && this.selectedR.length) {
      // Handle form submission
      const point: Point = {
        x: this.selectedX[0],
        y: this.y,
        r: this.selectedR[0],
        result: false,
        timestamp: new Date()
      };

      this.pointService.checkPoint(point).subscribe(result => {
        this.points = [...this.points, result];
      });
    }
  }
}