package app.service;

import app.model.Point;
import app.model.User;
import app.repository.PointRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class PointIndexService {

    @EJB
    private PointRepository pointRepository;

    public List<Point> getAllByUser(User user) {
        return pointRepository.getPointsByUser(user);
    }
}
