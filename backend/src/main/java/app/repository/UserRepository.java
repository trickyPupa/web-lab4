package app.repository;

import app.model.User;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Stateless
@Log4j2
public class UserRepository implements Serializable {

    @PersistenceContext(unitName = "web4")
    private EntityManager em;

    public User findByUsername(String username) {
        return em.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username",
                        User.class
                )
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public User findById(long id) {
        return em.find(User.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(User user) {
        em.persist(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    public void init() {
        log.info("Initializing UserRepository " + em.toString());
    }
}
