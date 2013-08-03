package net.benas.todolist.core.repository.impl;

import net.benas.todolist.core.domain.User;
import net.benas.todolist.core.repository.api.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link UserRepository}
 * @author benas (md.benhassine@gmail.com)
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    public User create(final User user) {
        em.persist(user);
        return user;
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void remove(final User user) {
        em.createNativeQuery("DELETE FROM todo t WHERE t.userId = " + user.getId()).executeUpdate();
        User u = em.find(User.class, user.getId()); //Entity must be attached to the PC before being removed, or else => java.lang.IllegalArgumentException. Entity must be managed to call remove (em.merge(odo) does not fix the pb).
        em.remove(u);
    }

    public User getUserById(final long id) {
        return em.find(User.class, id);
    }

    public User getUserByEmail(final String email) {
        TypedQuery<User> query = em.createNamedQuery("findUserByEmail", User.class);
        query.setParameter("p_email", email);
        List<User> users = query.getResultList();
        return (users != null && !users.isEmpty()) ? users.get(0) : null;
    }

    public boolean login(final String email, final String password) {
        TypedQuery<User> query = em.createNamedQuery("findUserByEmailAndPassword", User.class);
        query.setParameter("p_email", email);
        query.setParameter("p_password", password);
        List<User> users = query.getResultList();
        return (users != null && !users.isEmpty());
    }

}
