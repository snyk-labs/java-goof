<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

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

                <s:form namespace="/todo" action="export.do" method="post" cssClass="form-horizontal">

                    <fieldset>

                        <div class="control-group">
                            <label class="control-label" for="filename">File name:</label>
                            <div class="controls">
                                <s:textfield id="filename" name="exportForm.fileName" required="required" autofocus="autofocus" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="statusFilter">Status filter:</label>
                            <div class="controls">
                                <s:select id="statusFilter" name="exportForm.status" list="#{'DONE':'Done', 'TODO':'Todo'}" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="priorityFilter">Priority filter:</label>
                            <div class="controls">
                                <s:select id="priorityFilter" name="exportForm.priority" list="#{'LOW':'Low', 'MEDIUM':'Medium', 'HIGH':'High'}" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="format">Export Format:</label>
                            <div class="controls">
                                <s:select id="format" name="exportForm.exportFormat" list="#{'PDF':'Pdf', 'XML':'Xml', 'JSON':'Json'}" />
                            </div>
                        </div>

                        <div class="form-actions">
                            <s:submit cssClass="btn btn-primary" value="Export"/>
                            <button type="button" class="btn" onclick="history.go(-1)">Cancel</button>
                        </div>

                    </fieldset>

                </s:form>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
