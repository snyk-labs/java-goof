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

package net.benas.todolist.web.struts.action.user;

import com.opensymphony.xwork2.Action;
import net.benas.todolist.core.domain.Status;
import net.benas.todolist.core.domain.Todo;
import net.benas.todolist.core.domain.User;
import net.benas.todolist.web.struts.action.BaseAction;

import java.util.List;

/**
 * Action class to load user's todo list in home page.
 *
 * benas (md.benhassine@gmail.com)
 */
public class HomeAction extends BaseAction {

    List<Todo> todoList;

    public String execute() {
        User user = getSessionUser();
        todoList = todoService.getTodoListByUser(user.getId());
        return Action.SUCCESS;
    }

    /*
     * Getters for model attributes
     */

    public List<Todo> getTodoList() {
        return todoList;
    }

    public String getHomeTabStyle() {
        return "active";
    }

    public int getTotalCount() {
        return todoService.getTodoListByUser(getSessionUser().getId()).size();
    }

    public int getDoneCount() {
        return todoService.getTodoListByStatus(getSessionUser().getId(), Status.DONE).size();
    }

    public int getTodoCount() {
        return todoService.getTodoListByStatus(getSessionUser().getId(), Status.TODO).size();
    }

}
