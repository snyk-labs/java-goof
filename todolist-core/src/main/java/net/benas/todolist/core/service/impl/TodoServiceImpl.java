/*
 * The MIT License
 *
 * Copyright (c) 2013, benas (md.benhassine@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.benas.todolist.core.service.impl;

import net.benas.todolist.core.domain.Priority;
import net.benas.todolist.core.domain.Status;
import net.benas.todolist.core.domain.Todo;
import net.benas.todolist.core.repository.api.TodoRepository;
import net.benas.todolist.core.service.api.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link TodoService]
 * @author benas (md.benhassine@gmail.com)
 */
@Service
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo getTodoById(final long id) {
        return todoRepository.getTodoById(id);
    }

    public List<Todo> getTodoListByUser(final long userId) {
        return todoRepository.getTodoListByUser(userId);
    }

    public List<Todo> getTodoListByStatus(final long userId, final Status status) {
        return todoRepository.getTodoListByUserAndStatus(userId, status);
    }

    public List<Todo> getTodoListByPriority(final long userId, final Priority priority) {
        return todoRepository.getTodoListByUserAndPriority(userId, priority);
    }

    public List<Todo> getTodoListByStatusAndPriority(final long userId, final Status status, final Priority priority) {
        return todoRepository.getTodoListByUserAndStatusAndPriority(userId, status, priority);
    }

    public List<Todo> searchTodoListByTitleByUserId(final String title, final long userId) {
        return todoRepository.searchTodoListByTitleByUserId(title, userId);
    }

    @Transactional
    public Todo update(Todo todo) {
        return todoRepository.update(todo);
    }

    @Transactional
    public Todo create(final Todo todo) {
        return todoRepository.create(todo);
    }

    @Transactional
    public void remove(final Todo todo) {
        todoRepository.remove(todo);
    }
}
