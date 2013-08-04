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
                    <h1>Update todo</h1>
                </div>

                <form action="/user/todos/update.do" method="post" class="form-horizontal">

                    <fieldset>

                        <div class="control-group">
                            <label class="control-label" for="id">Todo Id:</label>
                            <div class="controls">
                                <input type="text" id="id" name="id" value="${requestScope.todo.id}" disabled="disabled"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="title">Title:</label>
                            <div class="controls">
                                <input type="text" id="title" name="title" value="${requestScope.todo.title}" required="required" autofocus="autofocus" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="dueDate">Due date:</label>
                            <div class="controls">
                                <input type="text" id="dueDate" name="dueDate" value="${requestScope.todo.dueDate}" required="required" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="status">Status:</label>
                            <div class="controls">
                                <select id="status" name="status">
                                  <option value="TODO">Todo</option>
                                  <option value="DONE">Done</option>
                              </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="priority">Priority:</label>
                            <div class="controls">
                                <select id="priority" name="priority">
                                  <option value="LOW">Low</option>
                                  <option value="MEDIUM">Medium</option>
                                  <option value="HIGH">High</option>
                              </select>
                            </div>
                        </div>

                        <input type="hidden" value="${requestScope.todo.userId}"/>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary"> <i class="icon-refresh icon-white"></i> Update</button>
                            <button type="button" class="btn" onclick="history.go(-1)"><i class="icon-remove"></i> Cancel</button>
                        </div>

                    </fieldset>

                    <script>
                        $('#dueDate').datepicker({
                            format : 'yyyy-mm-dd'
                        });
                    </script>

                </form>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
