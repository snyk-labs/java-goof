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

package io.github.benas.todolist.web.servlet.user;

import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import io.github.benas.todolist.web.common.util.TodoListUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet that controls action on the user's home page.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */

@WebServlet(name = "HomeServlet" ,urlPatterns = "/todos")
public class HomeServlet extends HttpServlet {

    private TodoService todoService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        todoService = applicationContext.getBean(TodoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);
        List<Todo> todoList = todoService.getTodoListByUser(user.getId());

        //todo list is request scoped to avoid storing and synchronizing it in session for each CRUD operation
        request.setAttribute("todoList", todoList);
        request.setAttribute("homeTabStyle", "active");

        int totalCount = todoList.size();
        int doneCount = TodoListUtils.countTotalDone(todoList);
        int todoCount = totalCount - doneCount;
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("doneCount", doneCount);
        request.setAttribute("todoCount", todoCount);

        request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
