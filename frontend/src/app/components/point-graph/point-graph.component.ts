import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Point } from '../../models/point.model';

@Component({
  selector: 'app-point-graph',
  templateUrl: './point-graph.component.html',
  styleUrls: ['./point-graph.component.scss']
})
export class PointGraphComponent {
  @Input() points: Point[] = [];
  @Input() currentR: number = 0;
  @Output() pointClick = new EventEmitter<Point>();
}