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
                    <h1>Change my password</h1>
                </div>
                <form action="/user/account/password.do" method="post" class="form-horizontal">

                    <fieldset>

                    <%@ include file="../../common/error.jspf"%>

                    <div class="control-group">
                        <label class="control-label" for="currentpassword">Current password :</label>
                        <div class="controls">
                            <input type="password" id="currentpassword" class="input-medium" placeholder="min 6 characters"/>
                            <%--<p class="help-block"><sf:errors path="currentpassword" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">New password :</label>
                        <div class="controls">
                            <input type="password" id="password" class="input-medium" placeholder="min 6 characters"/>
                            <%--<p class="help-block"><sf:errors path="password" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="confirmpassword">Confirm Password :</label>
                        <div class="controls">
                            <input type="password" id="confirmpassword" class="input-medium" placeholder="min 6 characters"/>
                            <%--<p class="help-block"><sf:errors path="confirmpassword" cssClass="error"/></p>--%>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="icon-refresh icon-white"></i> Update</button>
                        <a class="btn" href="/user/account"><i class="icon-remove"></i> Cancel</a>
                    </div>
                    </fieldset>

                </form>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../../common/footer.jspf"%>
