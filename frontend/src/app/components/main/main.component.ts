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
    { label: '1', value: 1 },
    { label: '2', value: 2 },
    { label: '3', value: 3 },
    { label: '4', value: 4 },
    { label: '5', value: 5 }
  ];
  selectedR: number[] = [3];
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

  onXSelect(event: any): void {
    this.selectedX = [event.value[event.value.length - 1]];
  }

  onRSelect(event: any): void {
    this.selectedR = [event.value[event.value.length - 1]];
  }

  checkPoint(clickedPoint?: Point): void {
    let pointToCheck: Point;

    if (clickedPoint) {
      pointToCheck = clickedPoint;
    } else if (this.selectedX.length && this.y !== null && this.selectedR.length) {
      pointToCheck = {
        x: this.selectedX[0],
        y: this.y,
        r: this.selectedR[0],
        result: false
      };
    } else {
      return;
    }

    this.pointService.checkPoint(pointToCheck).subscribe({
      next: (result) => {
        this.points = [...(this.points || []), result];
      },
      error: (error) => {
        console.error('Error checking point:', error);
      }
    });
  }

  clearPoints(): void {
    this.pointService.deletePoints().subscribe({
      next: () => {
        this.loadPoints();
      },
      error: (error) => {
        console.error('Error deleting points:', error);
      }
    });
  }
}