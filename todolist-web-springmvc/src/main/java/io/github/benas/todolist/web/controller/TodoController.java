/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.github.benas.todolist.web.controller;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.ExportService;
import io.github.todolist.core.service.api.TodoService;
import io.github.todolist.core.util.ExportFormat;
import io.github.benas.todolist.web.common.util.TodolistUtils;
import io.github.benas.todolist.web.util.ExportFormatPropertyEditor;
import io.github.benas.todolist.web.util.SessionData;
import io.github.benas.todolist.web.util.TodoPriorityPropertyEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller for todo related actions.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@Controller
public class TodoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SessionData sessionData;

    @Autowired
    private MessageSource messageProvider;

    @Autowired
    private TodoService todoService;

    @Autowired
    private ExportService exportService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TodolistUtils.DATE_FORMAT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(Priority.class, new TodoPriorityPropertyEditor());
        binder.registerCustomEditor(ExportFormat.class, new ExportFormatPropertyEditor());
    }

    /**********************
    * Create a new Todo
    **********************/

    @RequestMapping("/user/todos/new")
    public String redirectToCreateTodoPage(Model model) {
        model.addAttribute("today", new SimpleDateFormat(TodolistUtils.DATE_FORMAT).format(new Date()));
        model.addAttribute("todo", new Todo());
        return "todo/create";
    }

    @RequestMapping(value = "/user/todos/new.do", method = RequestMethod.POST)
    public String doCreateTodo(@ModelAttribute Todo todo) {
        final User user = sessionData.getUser();
        todo.setDone(false);
        todo.setUserId(user.getId());
        todoService.create(todo);
        return "redirect:/user/todos";
    }

    /**********************
    * Update a Todo
    **********************/

    @RequestMapping("/user/todos/{todoId}/update")
    public String redirectToUpdateTodoPage(@PathVariable long todoId, Model model) {
        Todo todo = todoService.getTodoById(todoId);
        model.addAttribute(todo);
        return "todo/update";
    }

    @RequestMapping(value = "/user/todos/update.do", method = RequestMethod.POST)
    public String doUpdateTodo(@ModelAttribute Todo todo) {
        todoService.update(todo);
        return "redirect:/user/todos";
    }

    /**********************
    * Delete Todo
    **********************/

    @RequestMapping(value = "/user/todos/{todoId}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTodo(@PathVariable long todoId) {
        ModelAndView modelAndView = new ModelAndView();
        Todo todo = todoService.getTodoById(todoId);
        if (todo == null) {
            modelAndView.addObject("error", messageProvider.getMessage("no.such.todo", new Object[]{todoId}, sessionData.getLocale()));
            modelAndView.setViewName("error");
        } else {
            todoService.remove(todo);
            modelAndView.setViewName("redirect:/user/todos");
        }
        return modelAndView;
    }

    /**********************
    * Search Todo
    **********************/

    @RequestMapping(value = "/user/todos/search", method = RequestMethod.GET)
    public String searchTodoList(@RequestParam String title, Model model) {
        List<Todo> todoList = todoService.searchTodoListByTitle(sessionData.getUser().getId(), "%" + title + "%");
        model.addAttribute("todoList", todoList);
        model.addAttribute("title", title);
        return "todo/search";
    }


    /**********************
    * Export Todo list
    **********************/

    @RequestMapping("/user/todos/export")
    public String redirectToExportTodoListPage() {
        return "todo/export";
    }

    @RequestMapping(value = "/user/todos/export.do", method = RequestMethod.POST)
    public void exportTodoList(@RequestParam String filename, @RequestParam boolean statusFilter,
                               @RequestParam Priority priorityFilter, @RequestParam ExportFormat format, HttpServletResponse response) {

        List<Todo> todoList = todoService.getTodoListByStatusAndPriority(sessionData.getUser().getId(), statusFilter, priorityFilter);

        byte[] bytes = exportService.exportTodoList(todoList, format);

        renderExportedTodoList(filename, format, response, bytes);

    }

    private void renderExportedTodoList(String filename, ExportFormat format, HttpServletResponse response, byte[] bytes) {
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
            String contentType = "text";
            if (format.equals(ExportFormat.PDF)) {
                contentType = "application";
            }
            response.setContentType(getContentType(format, contentType));
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", getContentDisposition(filename, format));
            servletOutputStream.write(bytes, 0, bytes.length);

        } catch (IOException e) {
            LOGGER.error("error while exporting todo list");
            //TODO show an error message to the user
        } finally {
            closeServletOutputStream(servletOutputStream);
        }
    }

    private String getContentDisposition(String filename, ExportFormat format) {
        return "attachment; filename=\"" + filename + "." + format.toString().toLowerCase() + "\"";
    }

    private String getContentType(ExportFormat format, String contentType) {
        return contentType + "/" + format.toString().toLowerCase();
    }

    private void closeServletOutputStream(ServletOutputStream servletOutputStream) {
        if (servletOutputStream != null) {
            try {
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close servletOutputStream", e);
            }
        }
    }
}
