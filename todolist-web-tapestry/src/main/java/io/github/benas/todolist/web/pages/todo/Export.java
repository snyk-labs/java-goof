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

package io.github.benas.todolist.web.pages.todo;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Status;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.ExportService;
import io.github.todolist.core.service.api.TodoService;
import io.github.todolist.core.util.ExportFormat;
import io.github.benas.todolist.web.data.TodoStreamResponse;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author benas (md.benhassine@gmail.com)
 */
@SuppressWarnings("unused")
public class Export {

    @SessionState
    private User loggedUser;

    @Inject
    private TodoService todoService;

    @Inject
    private ExportService exportService;

    @Property
    private String filename;

    @Property
    private Status statusFilter;

    @Property
    private Priority priorityFilter;

    @Property
    private ExportFormat format;

    @Property
    @Component(id = "exportTodoListForm")
    private Form exportForm;

    @OnEvent(value = EventConstants.SUCCESS, component = "exportTodoListForm")
    public StreamResponse exportTodoList() {
        List<Todo> todoList = todoService.getTodoListByStatusAndPriority(loggedUser.getId(), statusFilter, priorityFilter);
        byte[] bytes = exportService.exportTodoList(todoList, format);
        return new TodoStreamResponse(new ByteArrayInputStream(bytes), format, filename);
    }

}
