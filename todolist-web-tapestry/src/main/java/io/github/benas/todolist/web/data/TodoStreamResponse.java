/*
 * The MIT License
 *
 * Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package io.github.benas.todolist.web.data;

import io.github.todolist.core.util.ExportFormat;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author benas (md.benhassine@gmail.com)
 */
public class TodoStreamResponse implements StreamResponse {

    private InputStream is;
    private String filename = "myTodoList";
    private String fileExtension = "json";
    private String contentType = "text/json";

    public TodoStreamResponse(InputStream is, ExportFormat exportFormat, String... args) {
        this.is = is;
        if (exportFormat.equals(ExportFormat.PDF)) {
            contentType = "application/pdf";
            fileExtension = "pdf";
        } else if (exportFormat.equals(ExportFormat.XML)) {
            contentType = "text/xml";
            fileExtension = "xml";
        }
        if (args != null) {
            this.filename = args[0];
        }
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getStream() throws IOException {
        return is;
    }

    public void prepareResponse(Response response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + filename + "." + fileExtension);
    }
}