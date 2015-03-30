package io.github.benas.todolist.web.common.form;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Status;
import io.github.todolist.core.util.ExportFormat;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Export todo list form backing bean.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ExportForm {

    @NotEmpty(message = "{export.error.filename.required}")
    private String fileName;

    /*
     * Status, priority and export format will always be selected from a drop down list and they will be provided anyway
     */

    private Status status;

    private Priority priority;

    private ExportFormat exportFormat;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public ExportFormat getExportFormat() {
        return exportFormat;
    }

    public void setExportFormat(ExportFormat exportFormat) {
        this.exportFormat = exportFormat;
    }
}
