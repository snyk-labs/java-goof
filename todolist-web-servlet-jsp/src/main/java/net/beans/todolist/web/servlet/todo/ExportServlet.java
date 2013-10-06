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

import net.benas.todolist.core.domain.Priority;
import net.benas.todolist.core.domain.Status;
import net.benas.todolist.core.domain.Todo;
import net.benas.todolist.core.domain.User;
import net.benas.todolist.core.service.api.ExportService;
import net.benas.todolist.core.service.api.TodoService;
import net.benas.todolist.core.util.ExportFormat;
import net.benas.todolist.web.common.util.TodolistUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author benas (md.benhassine@gmail.com)
 */

@WebServlet(name = "ExportServlet", urlPatterns = {"/todos/export", "/todos/export.do"})
public class ExportServlet extends HttpServlet {

    private TodoService todoService;

    private ExportService exportService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        todoService = applicationContext.getBean(TodoService.class);
        exportService = applicationContext.getBean(ExportService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/todo/export.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodolistUtils.SESSION_USER);

        String filename = request.getParameter("filename");
        String statusFilter = request.getParameter("statusFilter");
        String priorityFilter = request.getParameter("priorityFilter");
        String format = request.getParameter("format");

        List<Todo> todoList = todoService.getTodoListByStatusAndPriority(user.getId(), Status.valueOf(statusFilter), Priority.valueOf(priorityFilter));

        ServletOutputStream servletOutputStream = response.getOutputStream();
        byte[] bytes = exportService.exportTodoList(todoList, ExportFormat.valueOf(format.trim().toUpperCase()));

        String contentType = "text";
        if (format.equalsIgnoreCase(ExportFormat.PDF.toString()))
               contentType = "application";
        response.setContentType(contentType + "/" + format);
        response.setContentLength(bytes.length);
        response.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "." + format + "\"" );

        servletOutputStream.write(bytes, 0, bytes.length);
        servletOutputStream.flush();
        servletOutputStream.close();

    }

}
