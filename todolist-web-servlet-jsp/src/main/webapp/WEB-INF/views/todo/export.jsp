<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf"%>

<div class="container">
    <div class="row">
        <div class="span3">
            <%@ include file="../common/sidebar.jspf"%>
        </div>
        <div class="span9">
            <div class="well">
                <div class="page-header">
                    <h1>Export my Todo list</h1>
                </div>

                <form action="/todos/export.do" method="post" class="form-horizontal">

                    <fieldset>

                        <div class="control-group">
                            <label class="control-label" for="filename">File name:</label>
                            <div class="controls">
                                <input type="text" id="filename" name="filename" required="required" autofocus="autofocus" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="statusFilter">Status filter:</label>
                            <div class="controls">
                                <select id="statusFilter" name="statusFilter">
                                    <option value="true">Done</option>
                                    <option value="false">Todo</option>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="priorityFilter">Priority filter:</label>
                            <div class="controls">
                                <select id="priorityFilter" name="priorityFilter">
                                    <option value="LOW">Low</option>
                                    <option value="MEDIUM">Medium</option>
                                    <option value="HIGH">High</option>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="format">Export Format:</label>
                            <div class="controls">
                                <select id="format" name="format">
                                    <option value="PDF">Pdf</option>
                                    <option value="XML">Xml</option>
                                    <option value="JSON">Json</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary"> <i class="icon-download-alt icon-white"></i> Export </button>
                            <button type="button" class="btn" onclick="history.go(-1)">Cancel</button>
                        </div>

                    </fieldset>

                </form>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
