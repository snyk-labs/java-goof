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

                <c:if test="${error != null}">
                    <div class="alert alert-error">
                        <strong>${error}</strong>
                    </div>
                </c:if>

                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="firstname">First Name :</label>
                        <div class="controls">
                            <sf:input path="firstname" id="firstname" type="text" class="input-medium"/>
                            <p class="help-block"><sf:errors path="firstname" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="lastname">Last Name :</label>
                        <div class="controls">
                            <sf:input path="lastname" id="lastname" type="text" class="input-medium"/>
                            <p class="help-block"><sf:errors path="lastname" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email :</label>
                        <div class="controls">
                            <sf:input path="email" id="email" type="text" class="input-medium" placeholder="your@email.com"/>
                            <p class="help-block"><sf:errors path="email" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">Password :</label>
                        <div class="controls">
                            <sf:input type="password" path="password" id="password" class="input-medium" placeholder="min 6 characters"/>
                            <p class="help-block"><sf:errors path="password" cssClass="error"/></p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="confirmationPassword">Confirm Password :</label>
                        <div class="controls">
                            <sf:input type="password" path="confirmationPassword" id="confirmationPassword" class="input-medium" placeholder="min 6 characters"/>
                            <p class="help-block"><sf:errors path="confirmationPassword" cssClass="error"/></p>
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