package io.github.todolist.core.repository.impl;

import io.github.todolist.core.domain.User;
import io.github.todolist.core.repository.api.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link UserRepository} using JPA.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    public User create(final User user) {
        em.persist(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    public User update(User user) {
        return em.merge(user);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(final User user) {
        em.createNativeQuery("DELETE FROM todo t WHERE t.userId = " + user.getId()).executeUpdate();
        User u = em.find(User.class, user.getId());
        /*
         * FIXME : should be em.remove(user) + remove all his todos
         *
         * Entity must be attached to the PC before being removed, or else => java.lang.IllegalArgumentException.
         * Entity must be managed to call remove(em.merge(user)) does not fix the pb).
         */
        em.remove(u);
    }

    /**
     * {@inheritDoc}
     */
    public User getUserById(final long id) {
        return em.find(User.class, id);
    }

    /**
     * {@inheritDoc}
     */
    public User getUserByEmail(final String email) {
        TypedQuery<User> query = em.createNamedQuery("findUserByEmail", User.class);
        query.setParameter("p_email", email);
        List<User> users = query.getResultList();
        return (users != null && !users.isEmpty()) ? users.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean login(final String email, final String password) {
        TypedQuery<User> query = em.createNamedQuery("findUserByEmailAndPassword", User.class);
        query.setParameter("p_email", email);
        query.setParameter("p_password", password);
        List<User> users = query.getResultList();
        return (users != null && !users.isEmpty());
    }

}
