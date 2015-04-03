/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.todolist.web.servlet.todo;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Status;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.service.api.TodoService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Servlet that controls todo update.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */

@WebServlet(name = "UpdateTodoServlet",urlPatterns = {"/todos/update", "/todos/update.do"})
public class UpdateTodoServlet extends HttpServlet {

    private TodoService todoService;

    private ResourceBundle resourceBundle;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        todoService = applicationContext.getBean(TodoService.class);
        resourceBundle = ResourceBundle.getBundle("todolist");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            long todoId = Long.parseLong(request.getParameter("todoId"));
            Todo todo = todoService.getTodoById(todoId); // FIXME security issue : may provide an id for a todo of another user!
            request.setAttribute("todo",todo);
            request.getRequestDispatcher("/WEB-INF/views/todo/update.jsp").forward(request, response);
        }catch (NumberFormatException e) {
            request.setAttribute("error", MessageFormat.format(resourceBundle.getString("no.such.todo"), request.getParameter("todoId")));
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long todoId = Long.parseLong(request.getParameter("todoId"));
        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");

        //TODO populate a Todo bean and validate it using JSR 303

        Todo todo = todoService.getTodoById(todoId);
        todo.setTitle(title);
        todo.setDueDate(new Date(dueDate));
        todo.setStatus(Status.valueOf(status));
        todo.setPriority(Priority.valueOf(priority));
        todoService.update(todo);
        request.getRequestDispatcher("/todos").forward(request, response);

    }

}
