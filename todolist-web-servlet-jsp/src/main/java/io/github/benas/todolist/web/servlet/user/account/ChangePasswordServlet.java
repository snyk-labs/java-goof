/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.todolist.web.servlet.user.account;

import io.github.benas.todolist.web.common.form.ChangePasswordForm;
import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

import static io.github.benas.todolist.web.util.Views.ACCOUNT_PAGE;

/**
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */

@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/user/account/password.do")
public class ChangePasswordServlet extends HttpServlet {

    private UserService userService;

    private ResourceBundle resourceBundle;

    private Validator validator;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        userService = applicationContext.getBean(UserService.class);
        resourceBundle = ResourceBundle.getBundle("todolist");

        //initialize JSR 303 validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmationPassword = request.getParameter("confirmationPassword");

        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setCurrentPassword(currentPassword);
        changePasswordForm.setNewPassword(newPassword);
        changePasswordForm.setConfirmationPassword(confirmationPassword);

        String nextPage = ACCOUNT_PAGE;

        validatePasswords(request, changePasswordForm);

        if (isInvalid(request)) {
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);

        if (!confirmationPassword.equals(newPassword)) {
            request.setAttribute("errorConfirmationPassword", resourceBundle.getString("account.password.confirmation.error"));
            addGlobalChangePasswordErrorAttribute(request);
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        if (!currentPassword.equals(user.getPassword())) {
            request.setAttribute("errorCurrentPassword", resourceBundle.getString("account.password.error"));
            addGlobalChangePasswordErrorAttribute(request);
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        user.setPassword(newPassword);
        userService.update(user);
        session.setAttribute(TodoListUtils.SESSION_USER, user);
        request.setAttribute("updatePasswordSuccessMessage", resourceBundle.getString("account.password.update.success"));
        request.getRequestDispatcher("/user/account").forward(request, response);

    }

    private void validatePasswords(HttpServletRequest request, ChangePasswordForm changePasswordForm) {
        validateCurrentPassword(request, changePasswordForm);

        validateNewPassword(request, changePasswordForm);

        validateConfirmationPassword(request, changePasswordForm);
    }

    private void validateConfirmationPassword(HttpServletRequest request, ChangePasswordForm changePasswordForm) {
        Set<ConstraintViolation<ChangePasswordForm>> constraintViolations
                = validator.validateProperty(changePasswordForm, "confirmationPassword");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorConfirmationPassword", constraintViolations.iterator().next().getMessage());
            addGlobalChangePasswordErrorAttribute(request);
        }
    }

    private void validateNewPassword(HttpServletRequest request, ChangePasswordForm changePasswordForm) {
        Set<ConstraintViolation<ChangePasswordForm>> constraintViolations
                = validator.validateProperty(changePasswordForm, "newPassword");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorNewPassword", constraintViolations.iterator().next().getMessage());
            addGlobalChangePasswordErrorAttribute(request);
        }
    }

    private void validateCurrentPassword(HttpServletRequest request, ChangePasswordForm changePasswordForm) {
        Set<ConstraintViolation<ChangePasswordForm>> constraintViolations
                = validator.validateProperty(changePasswordForm, "currentPassword");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorCurrentPassword", constraintViolations.iterator().next().getMessage());
            addGlobalChangePasswordErrorAttribute(request);
        }
    }

    private void addGlobalChangePasswordErrorAttribute(HttpServletRequest request) {
        request.setAttribute("error", resourceBundle.getString("account.password.error.global"));
    }

    private boolean isInvalid(HttpServletRequest request) {
        return request.getAttribute("error") != null;
    }

}
