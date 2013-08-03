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
                    <h1>My account</h1>
                </div>
                <form class="form-horizontal">

                    <fieldset>
                        <legend>Personal informations</legend>

                        <div class="control-group">
                            <label class="control-label" for="identifier">User Id:</label>
                            <div class="controls">
                                <input name="identifier" id="identifier" value="${user.id}" type="text" class="input-medium" disabled="disabled" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="firstname">First Name :</label>
                            <div class="controls">
                                <input name="firstname" id="firstname" value="${user.firstname}" type="text" class="input-medium" disabled="disabled">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="lastname">Last Name :</label>
                            <div class="controls">
                                <input name="lastname" id="lastname" value="${user.lastname}" type="text" class="input-medium" disabled="disabled">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="email">Email :</label>
                            <div class="controls">
                                <input name="email" id="email" value="${user.email}" type="text" class="input-medium" disabled="disabled">
                            </div>
                        </div>

                        <div class="form-actions">
                            <div class="btn-group">
                                <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                                    Update my account <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="/user/account/update">Update my personal data</a></li>
                                    <li><a href="/user/account/password">Change my password</a></li>
                                    <li><a href="/user/account/delete">Remove my account</a></li>
                                </ul>
                            </div>
                        </div>

                    </fieldset>
                </form>

                <fieldset>
                    <legend>Todo statistics</legend>

                    <table class="table table-bordered table-striped">
                        <tbody>
                        <tr>
                            <td>Tasks to do</td>
                            <td><span class="badge">${todoCount}</span></td>
                        </tr>
                        <tr>
                            <td>Tasks done</td>
                            <td><span class="badge badge-success">${doneCount}</span></td>
                        </tr>
                        <tr>
                            <td>Total tasks</td>
                            <td><span class="badge badge-inverse">${totalCount}</span></td>
                        </tr>
                        </tbody>
                    </table>

                </fieldset>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../../common/footer.jspf"%>
