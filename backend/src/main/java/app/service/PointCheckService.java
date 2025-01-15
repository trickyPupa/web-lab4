package app.service;

import app.DTO.request.PointRequest;
import app.model.Point;
import app.model.User;
import app.repository.PointRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class PointCheckService {
    @EJB
    private PointRepository pointRepository;

    public Point createPoint(PointRequest dto, User user) {
        boolean result = checkAreaHit(dto);

        Point point = new Point();
        point.setX(dto.getX());
        point.setY(dto.getY());
        point.setR(dto.getR());
        point.setResult(result);

        pointRepository.save(point, user);

        return point;
    }

    public boolean checkAreaHit(PointRequest dto) {
        double x = dto.getX();
        double y = dto.getY();
        double r = dto.getR();

        if (x >= 0 && y <= 0 && y >= -r && x <= r) {
            return true;
        }
        else if (x <= 0 && y <= 0 && y >= -2 * x - r) {
            return true;
        }
        else if (x <= 0 && y >= 0 && x*x + y*y <= r*r/4) {
            return true;
        }
        return false;
    }
}
