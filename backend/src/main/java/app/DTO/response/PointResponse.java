package app.DTO.response;

import app.model.Point;
import lombok.Data;

@Data
public class PointResponse {
    private double x;
    private double y;
    private double r;
    private boolean result;

    public PointResponse(Point p) {
        this.x = p.getX();
        this.y = p.getY();
        this.r = p.getR();
        this.result = p.isResult();
    }
}