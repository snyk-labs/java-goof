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
                        <label class="control-label" for="firstName">First Name :</label>
                        <div class="controls">
                            <input id="firstName" name="firstName" type="text" class="input-medium" required="required"/>
                            <%--<p class="help-block"><sf:errors path="firstname" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="lastName">Last Name :</label>
                        <div class="controls">
                            <input id="lastName" name="lastName" type="text" class="input-medium" required="required"/>
                            <%--<p class="help-block"><sf:errors path="lastname" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email :</label>
                        <div class="controls">
                            <input id="email" name="email" type="email" class="input-medium" placeholder="your@email.com" required="required"/>
                            <%--<p class="help-block"><sf:errors path="email" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">Password :</label>
                        <div class="controls">
                            <input type="password" id="password" name="password" class="input-medium" placeholder="min 6 characters" required="required"/>
                            <%--<p class="help-block"><sf:errors path="password" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="confirmPassword">Confirm Password :</label>
                        <div class="controls">
                            <input type="password" id="confirmPassword" name="confirmPassword" class="input-medium" placeholder="min 6 characters" required="required"/>
                            <%--<p class="help-block"><sf:errors path="confirmationPassword" cssClass="error"/></p>--%>
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