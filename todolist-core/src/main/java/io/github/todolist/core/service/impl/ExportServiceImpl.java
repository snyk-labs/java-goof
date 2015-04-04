/*
 * The MIT License
 *
 * Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.todolist.core.service.impl;

import com.google.gson.Gson;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.thoughtworks.xstream.XStream;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.service.api.ExportService;
import io.github.todolist.core.util.ExportFormat;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of {@link ExportService}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ExportServiceImpl implements ExportService {

    private static final Logger LOGGER = Logger.getLogger(ExportServiceImpl.class.getName());

    private XStream xStream;

    private Gson gson;

    public ExportServiceImpl() {
        xStream = new XStream();
        xStream.alias("todo", Todo.class);
        gson = new Gson();
    }

    /**
     * {@inheritDoc}
     */
    public byte[] exportTodoList(final List<Todo> todoList, final ExportFormat exportFormat) {

        byte[] result = new byte[]{};

        // TODO replace with switch
        if (exportFormat.equals(ExportFormat.XML)) {
            result = getTodolistAsXml(todoList);
        }

        if (exportFormat.equals(ExportFormat.PDF)) {
            result = getTodolistAsPdf(todoList);

        }

        if (exportFormat.equals(ExportFormat.JSON)) {
            result = getTodolistAsJson(todoList);
        }

        return result;
    }

    private byte[] getTodolistAsPdf(List<Todo> todoList) {
        byte[] result = new byte[]{};
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            for (Todo todo :todoList) {
                document.add(new Paragraph(todo.toString()));
            }
            result = baos.toByteArray();
        } catch (DocumentException de) {
            LOGGER.log(Level.WARNING, "Unable to export todo list in pdf format", de);
        } finally {
            document.close();
        }

        return result;
    }

    private byte[] getTodolistAsXml(List<Todo> todoList) {
        return xStream.toXML(todoList).getBytes();
    }

    private byte[] getTodolistAsJson(List<Todo> todoList) {
        return gson.toJson(todoList).getBytes();
    }

}
