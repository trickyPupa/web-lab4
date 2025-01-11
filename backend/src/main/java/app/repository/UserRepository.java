package app.repository;

import app.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;

@Stateless
public class UserRepository implements Serializable {

    @PersistenceContext(unitName = "web3")
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

    public void save(User user) {
        em.persist(user);
    }

    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}
