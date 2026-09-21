<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tl" uri="http://todolist.org/taglib" %>
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
                    <h1>Search results for email: '${param.email}' </h1>
                </div>

                <table class="table table-bordered table-striped">

                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${requestScope.userList}" var="currentUser">
                        <tr>
                            <td>${currentUser.id}</td>
                            <td>${currentUser.name}</td>
                            <td><tl:highlight pattern="${param.email}" cssClass="label label-warning">${currentUser.email}</tl:highlight></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>

                <c:if test="${empty requestScope.userList}">
                    <div class="alert alert-info">
                        <div align="center">No user with email matching '${param.email}'</div>
                    </div>
                </c:if>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
