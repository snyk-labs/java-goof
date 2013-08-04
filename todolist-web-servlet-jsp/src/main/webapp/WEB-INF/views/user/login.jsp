<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf"%>
<%--content--%>

<div class="container">

    <div class="row">
        <div class="span6 offset3">
            <div class="page-header">
                <h1>Sign in</h1>
            </div>

            <%@ include file="../common/error.jspf"%>

            <form class="well form-horizontal" method="post" action="/login.do">
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="email">Email :</label>
                        <div class="controls">
                            <input id="email" type="text" class="input-medium" placeholder="your@email.com"/>
                            <%--<p class="help-block"><sf:errors path="email" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">Password :</label>
                        <div class="controls">
                            <input type="password" id="password" class="input-medium" placeholder="min 6 characters"/>
                            <%--<p class="help-block"><sf:errors path="password" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="icon-lock icon-white"></i> Sign in</button>
                    </div>

                    <div align="center">
                        You don't have an account yet? <a href="/register">Register here for free!</a>
                    </div>

                </fieldset>
            </form>

        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
