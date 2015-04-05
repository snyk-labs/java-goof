<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="span3">
            <%@ include file="../common/sidebar.jspf" %>
        </div>
        <div class="span9">
            <div class="well">
                <div class="page-header">
                    <h1>My settings</h1>
                </div>

                <%@ include file="../common/error.jspf"%>

                <div class="row">
                    <div class="span8">
                        <form action="/user/account/update.do" method="post" class="form-horizontal">
                            <fieldset>
                                <legend>Update my profile <p class="alert-success">${requestScope.updateProfileSuccessMessage}</p></legend>

                                <div class="control-group">
                                    <label class="control-label" for="id">Id:</label>

                                    <div class="controls">
                                        <input name="id" id="id" value="${sessionScope.user.id}" type="text" class="input-medium" disabled="disabled"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="name">Name:</label>

                                    <div class="controls">
                                        <input name="name" id="name" value="${sessionScope.user.name}" type="text" class="input-medium" required="required"/>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="email">Email:</label>

                                    <div class="controls">
                                        <input name="email" id="email" value="${sessionScope.user.email}" type="email" class="input-medium" required="required"/>
                                    </div>
                                </div>

                                <div class="form-actions">
                                    <button type="submit" class="btn btn-primary"><i class="icon-refresh icon-white"></i> Update my profile</button>
                                </div>

                            </fieldset>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <div class="span8">
                        <form action="/user/account/password.do" method="post" class="form-horizontal">

                            <fieldset>

                                <legend>Update my Password <p class="alert-success">${requestScope.updatePasswordSuccessMessage}</p></legend>

                                <div class="control-group">
                                    <label class="control-label" for="currentPassword">Current password:</label>

                                    <div class="controls">
                                        <input type="password" id="currentPassword" name="currentPassword" class="input-medium" placeholder="min 6 characters" required="required"/>
                                        <p class="help-block alert-error"><c:out value="${requestScope.errorCurrentPassword}"/></p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="newPassword">New password:</label>

                                    <div class="controls">
                                        <input type="password" id="newPassword" name="newPassword" class="input-medium" placeholder="min 6 characters" required="required"/>
                                        <p class="help-block alert-error"><c:out value="${requestScope.errorNewPassword}"/></p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="confirmationPassword">Confirmation password:</label>

                                    <div class="controls">
                                        <input type="password" id="confirmationPassword" name="confirmationPassword" class="input-medium" placeholder="min 6 characters" required="required"/>
                                        <p class="help-block alert-error"><c:out value="${requestScope.errorConfirmationPassword}"/></p>
                                    </div>
                                </div>

                                <div class="form-actions">
                                    <button type="submit" class="btn btn-primary"><i class="icon-refresh icon-white"></i> Update my password</button>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <div class="span8">
                        <fieldset>
                            <legend>Delete my account</legend>
                            <div class="alert alert-block alert-error fade in">
                                <p>You are about to remove your account from "Todolist MVC". This will completely
                                    delete all your data and you will be no more able to use your account.
                                    Please be sure.</p>
                                <p>
                                    <a class="btn btn-danger" data-toggle="modal" href="#deleteAccountLink"> <i class="icon-remove icon-white"></i> Delete my account</a>
                                </p>
                                <div class="modal hide" id="deleteAccountLink">
                                    <div class="modal-header">
                                        <h3>Confirmation</h3>
                                    </div>
                                    <div class="modal-body">
                                        <p>Are you sure to delete your account?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form class="form-horizontal" method="post" action="/user/account/delete.do">
                                            <p>
                                                <a href="#" class="btn" data-dismiss="modal">No, I'm not sure</a>
                                                <button type="submit" class="btn btn-danger">Yes, I do confirm!</button>
                                            </p>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf" %>
