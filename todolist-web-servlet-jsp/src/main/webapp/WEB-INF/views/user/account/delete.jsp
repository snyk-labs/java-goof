<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/header.jspf"%>

<div class="container">
    <div class="row">
        <div class="span3">
            <%@ include file="../../common/sidebar.jspf"%>
        </div>
        <div class="span9">
            <div class="well">
                <div class="page-header">
                	<h1>Remove my account</h1>
                </div>
                <form class="form-horizontal" method="post" action="/user/account/delete.do">
                    <div class="alert alert-block alert-error fade in">
                        <h4 class="alert-heading">Account removal</h4>
                        <p>You are about to remove your account from "Todolist MVC". This will completely delete all your data and you will be no more able to use your current account. Are you sure to remove your account?</p>
                        <p>
                            <button type="submit" class="btn btn-danger">Yes, I do confirm!</button>
                            <a class="btn" href="/user/account">No, I'm not sure</a>
                        </p>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../../common/footer.jspf"%>
