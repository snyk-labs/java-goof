/*
 * The MIT License
 *
 *   Copyright (c) 2013, benas (md.benhassine@gmail.com) (md.benhassine@gmail.com)
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

package net.beans.todolist.web.servlet.todo;

import net.benas.todolist.core.domain.Todo;
import net.benas.todolist.core.service.api.TodoService;
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
import java.util.ResourceBundle;

/**
 * @author benas (md.benhassine@gmail.com)
 */

@WebServlet(name = "DeleteTodoServlet",urlPatterns = "/user/todos/delete.do") // URL should be restful /user/todos/{todoId}/delete
public class DeleteTodoServlet extends HttpServlet {

    private TodoService todoService;

    private ResourceBundle resourceBundle;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        todoService = applicationContext.getBean(TodoService.class);
        resourceBundle = ResourceBundle.getBundle("todolist");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long todoId = Long.parseLong(request.getParameter("todoId"));
            Todo todo = todoService.getTodoById(todoId);
            if (todo != null) {
                todoService.remove(todo);
                request.getRequestDispatcher("/user/todos").forward(request, response);
            }  else {
                request.setAttribute("error", MessageFormat.format(resourceBundle.getString("no.such.todo"), todoId));
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", MessageFormat.format(resourceBundle.getString("no.such.todo"), request.getParameter("todoId")));
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }

    }
}
