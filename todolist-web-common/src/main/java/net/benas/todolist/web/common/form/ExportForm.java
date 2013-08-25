package net.benas.todolist.web.common.form;

import net.benas.todolist.core.domain.Priority;
import net.benas.todolist.core.domain.Status;
import net.benas.todolist.core.util.ExportFormat;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Export todo list form backing bean.
 * @author benas (md.benhassine@gmail.com)
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
