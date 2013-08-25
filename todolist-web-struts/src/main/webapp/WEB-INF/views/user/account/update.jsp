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
                    <h1>Update my account</h1>
                </div>
                <form action="/user/account/update.do" method="post" class="form-horizontal">

                    <fieldset>
                        <legend>Personal informations</legend>

                    <%@ include file="../../common/error.jspf"%>

                    <div class="control-group">
                        <label class="control-label" for="identifier">User Id:</label>
                        <div class="controls">
                            <input name="identifier" id="identifier" value="${sessionScope.user.id}" type="text" class="input-medium" disabled="disabled"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="firstname">First Name :</label>
                        <div class="controls">
                            <input name="firstname" id="firstname" value="${sessionScope.user.firstname}" type="text" class="input-medium" required="required"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="lastname">Last Name :</label>
                        <div class="controls">
                            <input name="lastname" id="lastname" value="${sessionScope.user.lastname}" type="text" class="input-medium" required="required"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email :</label>
                        <div class="controls">
                            <input name="email" id="email" value="${sessionScope.user.email}" type="text" class="input-medium" required="required"/>
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
