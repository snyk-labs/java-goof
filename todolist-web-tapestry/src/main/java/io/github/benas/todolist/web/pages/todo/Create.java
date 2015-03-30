/*
 * The MIT License
 *
 * Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.todolist.web.pages.todo;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Status;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import io.github.benas.todolist.web.pages.user.Home;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Date;

/**
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@SuppressWarnings("unused")
public class Create {

    @SessionState
    private User loggedUser;

    @Inject
    private TodoService todoService;

    @Property
    private String title;

    @Property
    private Priority priority;

    @Property
    private Date dueDate;

    @Property
    @InjectComponent
    private Form createTodoForm;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void init() {
        dueDate = new Date();
    }

    @OnEvent(value=EventConstants.SUCCESS,component="createTodoForm")
    public Object createTodo() {
        Todo todo = new Todo(loggedUser.getId(), title, Status.TODO, priority, dueDate);
        todoService.create(todo);
        return Home.class;
    }

}
