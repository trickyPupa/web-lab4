import { Component, ElementRef, Input, Output, EventEmitter, ViewChild, OnInit, OnChanges, SimpleChanges, HostListener } from '@angular/core';
import { Point } from '../../models/point.model';

@Component({
  selector: 'app-point-graph',
  templateUrl: './point-graph.component.html',
  styleUrls: ['./point-graph.component.scss']
})
export class PointGraphComponent implements OnInit, OnChanges {
  @ViewChild('canvas', { static: true }) canvasRef!: ElementRef<HTMLCanvasElement>;
  @Input() points: Point[] = [];
  @Input() currentR: number = 0;
  @Output() pointClick = new EventEmitter<Point>();

  private readonly STEP = 50;
  private readonly fillColor = "lightpink";

  ngOnInit() {
    this.drawGraph();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['currentR'] || changes['points']) {
      this.drawGraph();
    }
  }
  validate(x: number, y: number, r: number) : boolean {
    return (-5 <= x && x <= 5) && (-5 <= y && y <= 5) && (0 <= r && r <= 5);
  }

  @HostListener('click', ['$event'])
  onCanvasClick(event: MouseEvent) {
    if (!this.currentR) return;

    const canvas = this.canvasRef.nativeElement;
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;

    const xCoord = Math.round((x - centerX) / this.STEP * 100) / 100;
    const yCoord = Math.round((centerY - y) / this.STEP * 100) / 100;

    if (!this.validate(xCoord, yCoord, this.currentR)) return;

    const point: Point = {
      x: xCoord,
      y: yCoord,
      r: this.currentR,
      result: false
    };

    this.pointClick.emit(point);
  }


  private drawAxis(canvas: HTMLCanvasElement, context: CanvasRenderingContext2D) {
    context.beginPath();
    context.moveTo(0, canvas.height / 2);
    context.lineTo(canvas.width, canvas.height / 2);
    context.strokeStyle = 'black';
    context.lineWidth = 2;
    context.stroke();

    context.beginPath();
    context.moveTo(canvas.width / 2, 0);
    context.lineTo(canvas.width / 2, canvas.height);
    context.strokeStyle = 'black';
    context.lineWidth = 2;
    context.stroke();
  }

  private drawGrid(canvas: HTMLCanvasElement, context: CanvasRenderingContext2D) {
    context.beginPath();
    for (let x = 0; x <= canvas.width / this.STEP; x += 1) {
      context.moveTo(x * this.STEP, 0);
      context.lineTo(x * this.STEP, canvas.height);
      context.strokeStyle = 'lightgray';
      context.lineWidth = 1;
      context.stroke();
    }
    for (let y = 0; y < canvas.height / this.STEP; y += 1) {
      context.moveTo(0, y * this.STEP);
      context.lineTo(canvas.width, y * this.STEP);
      context.strokeStyle = 'lightgray';
      context.lineWidth = 1;
      context.stroke();
    }
  }

  private drawCircle(context: CanvasRenderingContext2D, x: number, y: number, r: number,
                     startAngle: number, endAngle: number, counterclockwise: boolean = false) {
    context.beginPath();
    context.moveTo(x, y);
    context.fillStyle = this.fillColor;
    context.arc(x, y, r, startAngle, endAngle, counterclockwise);
    context.closePath();
    context.fill();
  }

  private drawRect(context: CanvasRenderingContext2D, x: number, y: number, w: number, h: number) {
    context.beginPath();
    context.moveTo(x, y);
    context.fillStyle = this.fillColor;
    context.rect(x, y, w, h);
    context.closePath();
    context.fill();
  }

  private drawTriangle(context: CanvasRenderingContext2D, x1: number, y1: number,
                       x2: number, y2: number, x3: number, y3: number) {
    context.beginPath();
    context.moveTo(x1, y1);
    context.fillStyle = this.fillColor;
    context.lineTo(x2, y2);
    context.lineTo(x3, y3);
    context.closePath();
    context.fill();
  }

  private drawCoords(canvas: HTMLCanvasElement, context: CanvasRenderingContext2D) {
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const gap = 30;

    context.fillStyle = 'black';
    context.font = '1.25em Montserrat, sans-serif';
    context.textAlign = 'center';
    context.textBaseline = 'bottom';

    for (let x = -canvas.width / 2 / this.STEP + 1; x <= canvas.width / 2 / this.STEP - 1; x += 1) {
      context.fillText(x.toString(), centerX + this.STEP * x, centerY + gap);
      context.beginPath();
      context.moveTo(centerX + this.STEP * x, centerY - 5);
      context.lineTo(centerX + this.STEP * x, centerY + 5);
      context.strokeStyle = 'black';
      context.closePath();
      context.stroke();
    }

    for (let y = -canvas.height / 2 / this.STEP + 1; y < canvas.height / 2 / this.STEP - 1; y += 1) {
      if (y === 0) continue;
      context.fillText((-y).toString(), centerX + gap, centerY + this.STEP * y + 10);
      context.beginPath();
      context.moveTo(centerX - 5, centerY + this.STEP * y);
      context.lineTo(centerX + 5, centerY + this.STEP * y);
      context.strokeStyle = 'black';
      context.closePath();
      context.stroke();
    }
  }

  private drawPoints(context: CanvasRenderingContext2D) {
    const centerX = this.canvasRef.nativeElement.width / 2;
    const centerY = this.canvasRef.nativeElement.height / 2;

    this.points.forEach(point => {
      context.beginPath();
      context.arc(
          centerX + (point.x * this.STEP / point.r) * this.currentR,
          centerY - (point.y * this.STEP / point.r) * this.currentR,
          4,
          0,
          2 * Math.PI
      );
      context.fillStyle = point.result ? 'green' : 'red';
      context.fill();
      context.closePath();
    });
  }

  private drawGraph() {
    const canvas = this.canvasRef.nativeElement;
    const context = canvas.getContext('2d');
    if (!context || !this.currentR) return;

    const R = this.STEP * this.currentR;

    canvas.width = 500;
    canvas.height = 500;

    this.drawGrid(canvas, context);
    this.drawCircle(context, canvas.width / 2, canvas.height / 2, R / 2, Math.PI, 3* Math.PI / 2, false);
    this.drawRect(context, canvas.width / 2, canvas.height / 2, R, R);
    this.drawTriangle(context, canvas.width / 2, canvas.height / 2,
        canvas.width / 2, canvas.height / 2 + R,
        canvas.width / 2 - R/2, canvas.height / 2);
    this.drawAxis(canvas, context);
    this.drawCoords(canvas, context);
    this.drawPoints(context);
  }
}