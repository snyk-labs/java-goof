package io.github.todolist.core.repository.api;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Status;
import io.github.todolist.core.domain.Todo;

import java.util.List;

/**
 * Interface for todo repository.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public interface TodoRepository {

    /**
     * Get todo by identifier.
     * @param id the todo identifier
     * @return the todo with identifier id
     */
    Todo getTodoById(final long id);

    /**
     * Get todo list for the given user.
     * @param userId the user identifier
     * @return the todo list for the given user
     */
    List<Todo> getTodoListByUser(final long userId);

    /**
     * Get todo list by status for the given user.
     * @param userId the user identifier
     * @param status the todo {@link Status}
     * @return the todo list for the given user
     */
    List<Todo> getTodoListByUserAndStatus(final long userId, final Status status);

    /**
     * Get todo list by priority for the given user.
     * @param userId the user identifier
     * @param priority the todo {@link Priority}
     * @return the todo list for the given user
     */
    List<Todo> getTodoListByUserAndPriority(final long userId, final Priority priority);

    /**
     * Get todo list by status and priority for the given user.
     * @param userId the user identifier
     * @param status the todo {@link Status}
     * @param priority the todo {@link Priority}
     * @return the todo list for the given user
     */
    List<Todo> getTodoListByUserAndStatusAndPriority(final long userId, final Status status, final Priority priority);

    /**
     * Search todo list by title for the given user.
     * @param title the todo title
     * @param userId the user identifier
     * @return the todo list containing the 'title' parameter in their title for the given user
     */
    List<Todo> searchTodoListByTitleByUserId(final String title, final long userId);

    /**
     * Update a todo.
     * @param todo the todo to update
     * @return the updated todo
     */
    Todo update(Todo todo);

    /**
     * Create a new todo.
     * @param todo the todo to create
     * @return the created todo
     */
    Todo create(final Todo todo);

    /**
     * Remove a todo.
     * @param todo the todo to remove
     */
    void remove(final Todo todo);

}
