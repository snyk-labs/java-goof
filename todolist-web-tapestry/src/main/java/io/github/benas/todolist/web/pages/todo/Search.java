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
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@SuppressWarnings("unused")
public class Search {

    @Inject
    private TodoService todoService;

    @SessionState
    private User loggedUser;

    @Property
    private List<Todo> todoList;

    @Property
    private Todo currentTodo;

    @Persist
    private String query;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void init() {
        todoList = todoService.searchTodoListByTitleByUserId("%" + query + "%", loggedUser.getId());
    }

    @OnEvent(value = EventConstants.ACTION, component = "deleteTodoLink")
    public void deleteTodo(long todoId) {
        Todo todo = todoService.getTodoById(todoId);
        if (todo != null) {
            todoService.remove(todo);
        }
    }

    public String getCurrentStatusLabel() {
        return currentTodo.getStatus().equals(Status.DONE) ? "label-success" : "";
    }

    public String getCurrentPriorityIcon() {
        String priorityIcon = "";
        if (currentTodo.getPriority().equals(Priority.HIGH)) {
            priorityIcon = "up";
        } else if (currentTodo.getPriority().equals(Priority.MEDIUM)) {
            priorityIcon = "right";
        } else if (currentTodo.getPriority().equals(Priority.LOW)) {
            priorityIcon = "down";
        }
        return priorityIcon;
    }

    public String getCurrentDueDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(currentTodo.getDueDate());
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCurrentHighlightedTitle() {
        return doHighlight(currentTodo.getTitle(), query);
    }

    /**
     * Apply a search/replace of the pattern in the input text.
     * @param input text to which apply the style for each pattern matched
     * @param pattern the pattern to highlight
     * @return the transformed text
     */
    private String doHighlight(final String input, final String pattern) {

        String cssClass = "label label-warning";
        String startSpanTag = "<span class=\"" + cssClass + "\">";
        String endSpanTag = "</span>";

        StringBuilder stringBuilder = new StringBuilder(startSpanTag);
        stringBuilder.append(pattern);
        stringBuilder.append(endSpanTag);

        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(input);

        return matcher.replaceAll(stringBuilder.toString());

    }
}
