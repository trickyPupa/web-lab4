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
  points: Point[] = [];

  xValues = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
  rValues = [1, 2, 3, 4, 5];

  y: number | null = null;
  selectedX: number[] = [];
  selectedR: number[] = [3];
  yError: string = '';
  isValid: boolean = false;

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

  onXChange(value: number, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      this.selectedX = [value];
    } else {
      this.selectedX = this.selectedX.filter(x => x !== value);
    }
    this.validateInput();
  }

  onRChange(value: number, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      this.selectedR = [value];
    } else {
      this.selectedR = this.selectedR.filter(r => r !== value);
    }
    this.validateInput();
  }

  validateInput() {
    if (this.y === null) {
      this.yError = 'Y coordinate is required';
    } else if (this.y < -5 || this.y > 5) {
      this.yError = 'Y must be between -5 and 5';
    } else {
      this.yError = '';
    }

    this.isValid = this.selectedX.length > 0 &&
        this.selectedR.length > 0 &&
        this.y !== null &&
        !this.yError;
  }
}