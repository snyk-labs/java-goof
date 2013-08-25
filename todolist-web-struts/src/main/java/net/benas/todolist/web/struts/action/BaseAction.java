/*
 * The MIT License
 *
 *   Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package net.benas.todolist.web.struts.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import net.benas.todolist.core.domain.User;
import net.benas.todolist.core.service.api.TodoService;
import net.benas.todolist.core.service.api.UserService;
import net.benas.todolist.web.common.util.TodolistUtils;

import java.util.Map;

/**
 * Base action declaring common services and objects.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class BaseAction extends ActionSupport {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected TodoService todoService;

    protected UserService userService;

    protected Map<String, Object> session = ActionContext.getContext().getSession();;

    protected User getSessionUser() {
        return (User) session.get(TodolistUtils.SESSION_USER);
    }

    /*
     * Setters for dependencies injection
     */

    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
