package app.repository;

import app.model.Point;
import app.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

@Stateless
@Log4j2
public class PointRepository implements Serializable {
    @PersistenceContext(unitName = "web4")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(Point point, User user) {
        point.setUser(user);
        em.persist(point);
    }

    public List<Point> getPointsByUser(User user) {
        return em.createQuery(
                        "SELECT p FROM Point p WHERE p.user.id = :userId",
                        Point.class
                )
                .setParameter("userId", user.getId())
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
