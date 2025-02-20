package app.service;

import app.DTO.request.CreatePointRequest;
import app.model.Point;
import app.model.User;
import app.repository.PointRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class PointService {
    @EJB
    private PointRepository pointRepository;

    public Point createPoint(CreatePointRequest dto, User user) {
        boolean result = checkAreaHit(dto);

        Point point = new Point();
        point.setX(dto.x());
        point.setY(dto.y());
        point.setR(dto.r());
        point.setResult(result);

        pointRepository.save(point, user);

        return point;
    }

    protected boolean checkAreaHit(CreatePointRequest dto) {
        double x = dto.x();
        double y = dto.y();
        double r = dto.r();

        if (x >= 0 && y >= 0 && y <= r && x <= r) {
            return true;
        }
        else if (x <= 0 && y <= 0 && y >= -x - r) {
            return true;
        }
        else if (x <= 0 && y >= 0 && x*x + y*y <= r*r/4) {
            return true;
        }
        return false;
    }

    public List<Point> getAllByUser(User user) {
        return pointRepository.getPointsByUser(user);
    }

    public void deleteAllByUser(User user) {
        pointRepository.deletePointsByUser(user);
    }
}
