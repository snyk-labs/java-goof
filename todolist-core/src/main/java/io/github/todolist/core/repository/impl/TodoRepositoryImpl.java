/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.github.todolist.core.repository.impl;

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
