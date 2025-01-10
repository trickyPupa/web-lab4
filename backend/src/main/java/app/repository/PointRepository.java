package app.repository;

import app.model.Point;
import app.model.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class PointRepository implements Serializable {
    @PersistenceContext(unitName = "web3")
    private EntityManager em;

    public void save(Point point, User user) {
        point.setUser(user);
        em.persist(point);
    }

    public List<Point> getPoints(User user) {
        return em.createQuery(
                        "SELECT p FROM Point p WHERE user_id = :userId",
                        Point.class
                )
                .setParameter("user_id", user.getId())
                .getResultList();
    }
}
