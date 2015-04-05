<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf"%>
<%--content--%>

<div class="container">

    <div class="row">
        <div class="span6 offset3">
            <div class="page-header">
                <h1>Sign up</h1>
            </div>

            <form class="well form-horizontal" method="post" action="/register.do">

                <%@ include file="../common/error.jspf"%>

                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="name">Name:</label>
                        <div class="controls">
                            <input id="name" name="name" type="text" class="input-medium" required="required"/>
                            <p class="help-block alert-error"><c:out value="${requestScope.errorName}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email:</label>
                        <div class="controls">
                            <input id="email" name="email" type="email" class="input-medium" placeholder="your@email.com" required="required"/>
                            <p class="help-block alert-error"><c:out value="${requestScope.errorEmail}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">Password:</label>
                        <div class="controls">
                            <input type="password" id="password" name="password" class="input-medium" placeholder="min 6 characters" required="required"/>
                            <p class="help-block alert-error"><c:out value="${requestScope.errorPassword}"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="confirmationPassword">Confirmation password:</label>
                        <div class="controls">
                            <input type="password" id="confirmationPassword" name="confirmationPassword" class="input-medium" placeholder="min 6 characters" required="required"/>
                            <p class="help-block alert-error"><c:out value="${requestScope.errorConfirmationPassword}"/></p>
                            <p class="help-block alert-error"><c:out value="${requestScope.errorConfirmationPasswordMatching}"/></p>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="icon-lock icon-white"></i> Sign up</button>
                    </div>

                    <div align="center">
                        You have already an account? <a href="/login">Sign in here</a>
                    </div>

                </fieldset>
            </form>

        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>