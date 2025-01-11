package app.service;

import app.DTO.PointDTO;
import app.model.Point;
import app.model.User;
import app.repository.PointRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class PointCheckService {
    @EJB
    private PointRepository pointRepository;

    public Point createPoint(PointDTO dto, User user) {
        boolean result = checkAreaHit(dto);

        Point point = new Point();
        point.setX(dto.getX());
        point.setY(dto.getY());
        point.setR(dto.getR());
        point.setResult(result);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        pointRepository.save(point, user);

        return point;
    }

    public boolean checkAreaHit(PointDTO dto) {
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
