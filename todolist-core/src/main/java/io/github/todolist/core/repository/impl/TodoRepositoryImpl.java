package io.github.todolist.core.repository.impl;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Status;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.repository.api.TodoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link TodoRepository} using JPA.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@Repository
public class TodoRepositoryImpl implements TodoRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    public Todo getTodoById(final long id) {
        return em.find(Todo.class, id);
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUser(final long userId) {
        TypedQuery<Todo> query = em.createNamedQuery("findTodosByUser", Todo.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUserAndStatus(final long userId, final Status status) {
        TypedQuery<Todo> query = em.createNamedQuery("findTodosByStatus", Todo.class);
        query.setParameter(1, userId);
        query.setParameter(2, status);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUserAndPriority(final long userId, final Priority priority) {
        TypedQuery<Todo> query = em.createNamedQuery("findTodosByPriority", Todo.class);
        query.setParameter(1, userId);
        query.setParameter(2, priority);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUserAndStatusAndPriority(final long userId, final Status status, final Priority priority) {
        TypedQuery<Todo> query = em.createNamedQuery("findTodosByStatusAndPriority", Todo.class);
        query.setParameter(1, userId);
        query.setParameter(2, status);
        query.setParameter(3, priority);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUserAndTitle(final long userId, final String title) {
        TypedQuery<Todo> query = em.createNamedQuery("findTodosByTitle", Todo.class);
        query.setParameter(1, userId);
        query.setParameter(2, title.toUpperCase());
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public Todo update(Todo todo) {
        return em.merge(todo);
    }

    /**
     * {@inheritDoc}
     */
    public Todo create(final Todo todo) {
        em.persist(todo);
        return todo;
    }

    /**
     * {@inheritDoc}
     */
    public void remove(final Todo todo) {
        Todo t = em.find(Todo.class, todo.getId());
        em.remove(t);
    }

}
