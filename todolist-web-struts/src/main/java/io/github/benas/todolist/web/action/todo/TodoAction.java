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

package io.github.benas.todolist.web.action.todo;

import com.opensymphony.xwork2.Action;
import io.github.todolist.core.domain.Todo;
import io.github.benas.todolist.web.action.BaseAction;

import java.text.MessageFormat;

/**
 * Action class for Todo CRUD operations.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class TodoAction extends BaseAction {

    private String error;

    private Todo todo;

    public String create() {
        return Action.SUCCESS;
    }

    public String doCreate() {
        todo.setUserId(getSessionUser().getId());
        todoService.create(todo);
        return Action.SUCCESS;
    }

    public String update() {
        todo = todoService.getTodoById(todo.getId());//populate the update form
        return Action.SUCCESS;
    }

    public String doUpdate() {
        todoService.update(todo);
        return Action.SUCCESS;
    }

    public String doDelete() {
        Todo todo = todoService.getTodoById(this.todo.getId());
        if (todo != null) {
            todoService.remove(todo);
            return Action.SUCCESS;
        }  else {
            error = MessageFormat.format(getText("no.such.todo"), this.todo.getId());
            return Action.ERROR;
        }
    }

    /*
    * Getters for model attributes
    */
    public Todo getTodo() {
        return todo;
    }

    public String getError() {
        return error;
    }

    /*
     * Setters for request parameters binding
     */
    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public void setTodoId(String todoId){
        this.todo = new Todo();
        todo.setId(Long.valueOf(todoId));
    }

}
