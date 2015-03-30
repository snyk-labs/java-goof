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

package io.github.benas.todolist.web.action.todo;

import com.opensymphony.xwork2.Action;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.service.api.ExportService;
import io.github.benas.todolist.web.common.form.ExportForm;
import io.github.benas.todolist.web.action.BaseAction;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Action class to export todo list.
 *
 * benas (mahmoud@benhassine.fr)
 */
public class ExportTodoAction extends BaseAction {

    private ExportService exportService;

    private ExportForm exportForm;

    private InputStream inputStream;

    public String export() {
        return Action.SUCCESS;
    }

    public String doExport() {
        List<Todo> todoList = todoService.getTodoListByStatusAndPriority(getSessionUser().getId(), exportForm.getStatus(), exportForm.getPriority());
        byte[] bytes = exportService.exportTodoList(todoList, exportForm.getExportFormat());
        inputStream = new ByteArrayInputStream(bytes);
        return Action.SUCCESS;
    }

    /*
     * Setters for dependency injection
     */

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    /*
     * Setters for request parameters binding
     */

    public void setExportForm(ExportForm exportForm) {
        this.exportForm = exportForm;
    }

    /*
    * Getters for model attributes
    */

    public ExportForm getExportForm() {
        return exportForm;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getFileName() {
        return exportForm.getFileName();
    }

    public String getContentType() {
        return "application/" + exportForm.getExportFormat().toString().toLowerCase();
    }
}
