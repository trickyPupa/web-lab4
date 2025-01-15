import { Component, OnInit } from '@angular/core';
import { PointService } from '../../services/point.service';
import { Point } from '../../models/point.model';

interface SelectOption {
  label: string;
  value: number;
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  xOptions: SelectOption[] = [
    { label: '-3', value: -3 },
    { label: '-2', value: -2 },
    { label: '-1', value: -1 },
    { label: '0', value: 0 },
    { label: '1', value: 1 },
    { label: '2', value: 2 },
    { label: '3', value: 3 },
    { label: '4', value: 4 },
    { label: '5', value: 5 }
  ];
  selectedX: number[] = [];
  y: number | null = null;
  rOptions: SelectOption[] = [
    { label: '-3', value: -3 },
    { label: '-2', value: -2 },
    { label: '-1', value: -1 },
    { label: '0', value: 0 },
    { label: '1', value: 1 },
    { label: '2', value: 2 },
    { label: '3', value: 3 },
    { label: '4', value: 4 },
    { label: '5', value: 5 }
  ];
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
      this.pointService.checkPoint(clickedPoint).subscribe(result => {
        this.points = [...this.points, result];
      });
    } else if (this.selectedX.length && this.y !== null && this.selectedR.length) {
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