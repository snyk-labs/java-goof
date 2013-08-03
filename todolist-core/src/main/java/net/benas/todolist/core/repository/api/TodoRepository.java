package net.benas.todolist.core.repository.api;

import net.benas.todolist.core.domain.Priority;
import net.benas.todolist.core.domain.Status;
import net.benas.todolist.core.domain.Todo;

import java.util.List;

/**
 * Interface for todo repository.
 * @author benas (md.benhassine@gmail.com)
 */
public interface TodoRepository {

    Todo getTodoById(final long id);

    List<Todo> getTodoListByUser(final long userId);

    List<Todo> getTodoListByUserAndStatus(final long userId, final Status status);

    List<Todo> getTodoListByUserAndPriority(final long userId, final Priority priority);

    List<Todo> getTodoListByUserAndStatusAndPriority(final long userId, final Status status, final Priority priority);

    List<Todo> searchTodoListByTitleByUserId(final String title, final long userId);

    Todo update(Todo todo);

    Todo create(final Todo todo);

    void remove(final Todo todo);

}
