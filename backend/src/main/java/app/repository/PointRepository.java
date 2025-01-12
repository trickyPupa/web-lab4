package app.repository;

import app.model.Point;
import app.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Stateless
public class PointRepository implements Serializable {
    @PersistenceContext(unitName = "web4")
    private EntityManager em;

    public void save(Point point, User user) {
        point.setUser(user);
        em.persist(point);
    }

    public List<Point> getPointsByUser(User user) {
        return em.createQuery(
                        "SELECT p FROM Point p WHERE user_id = :userId",
                        Point.class
                )
                .setParameter("user_id", user.getId())
                .getResultList();
    }

    public List<Point> getPoints() {
        return em.createQuery(
                        "SELECT p FROM Point p ",
                        Point.class
                )
                .getResultList();
    }
}
