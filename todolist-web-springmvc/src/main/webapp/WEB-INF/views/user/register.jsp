<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf"%>
<%--content--%>

<div class="container">

    <div class="row">
        <div class="span6 offset3">
            <div class="page-header">
                <h1>Sign up</h1>
            </div>

            <sf:form class="well form-horizontal" method="post" modelAttribute="registrationForm" action="/register.do">

                <%@ include file="../common/error.jspf"%>

                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="name">Name:</label>
                        <div class="controls">
                            <sf:input path="name" id="name" type="text" class="input-medium" required="required"/>
                            <p class="help-block alert-error"><sf:errors path="name" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email:</label>
                        <div class="controls">
                            <sf:input path="email" id="email" type="text" class="input-medium" placeholder="your@email.com" required="required"/>
                            <p class="help-block alert-error"><sf:errors path="email" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">Password:</label>
                        <div class="controls">
                            <sf:input type="password" path="password" id="password" class="input-medium" placeholder="min 6 characters" required="required"/>
                            <p class="help-block alert-error"><sf:errors path="password" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="confirmationPassword">Confirmation password:</label>
                        <div class="controls">
                            <sf:input type="password" path="confirmationPassword" id="confirmationPassword" class="input-medium" placeholder="min 6 characters" required="required"/>
                            <p class="help-block alert-error"><sf:errors path="confirmationPassword" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="icon-lock icon-white"></i> Sign up</button>
                    </div>

                    <div align="center">
                        You have already an account? <a href="/login">Sign in here</a>
                    </div>

                </fieldset>
            </sf:form>

        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>