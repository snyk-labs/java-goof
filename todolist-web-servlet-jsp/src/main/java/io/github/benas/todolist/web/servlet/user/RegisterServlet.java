/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.todolist.web.servlet.user;

import io.github.benas.todolist.web.common.form.RegistrationForm;
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
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Set;

import static io.github.benas.todolist.web.util.Views.REGISTER_PAGE;

/**
 * Servlet that controls the registration process.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register", "/register.do"})
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    private ResourceBundle resourceBundle;

    private Validator validator;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //initialize Spring user service
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        userService = applicationContext.getBean(UserService.class);

        //initialize JSR 303 validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        resourceBundle = ResourceBundle.getBundle("todolist");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("registerTabStyle", "active");
        request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmationPassword = request.getParameter("confirmationPassword");


        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setName(name);
        registrationForm.setEmail(email);
        registrationForm.setPassword(password);
        registrationForm.setConfirmationPassword(confirmationPassword);

        String nextPage = REGISTER_PAGE;

        checkName(request, registrationForm);

        checkEmail(request, registrationForm);

        checkPassword(request, registrationForm);

        checkConfirmationPassword(request, registrationForm);

        checkPasswordsMatch(request, password, confirmationPassword);

        if (isInvalid(request)) {
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        if (userService.getUserByEmail(email) != null) {
            request.setAttribute("error", MessageFormat.format(resourceBundle.getString("register.error.global.account"), email));
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        User user = new User(name, email, password);
        user = userService.create(user);
        HttpSession session = request.getSession(true);
        session.setAttribute(TodoListUtils.SESSION_USER, user);
        request.getRequestDispatcher("/todos").forward(request, response);

    }

    private boolean isInvalid(HttpServletRequest request) {
        return request.getAttribute("error") != null;
    }

    private void checkPasswordsMatch(HttpServletRequest request, String password, String confirmationPassword) {
        if (!confirmationPassword.equals(password)) {
            request.setAttribute("errorConfirmationPasswordMatching", resourceBundle.getString("register.error.password.confirmation.error"));
            addGlobalRegistrationErrorAttribute(request);
        }
    }

    private void checkConfirmationPassword(HttpServletRequest request, RegistrationForm registrationForm) {
        Set<ConstraintViolation<RegistrationForm>> constraintViolations;
        constraintViolations = validator.validateProperty(registrationForm, "confirmationPassword");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorConfirmationPassword", constraintViolations.iterator().next().getMessage());
            addGlobalRegistrationErrorAttribute(request);
        }
    }

    private void checkPassword(HttpServletRequest request, RegistrationForm registrationForm) {
        Set<ConstraintViolation<RegistrationForm>> constraintViolations;
        constraintViolations = validator.validateProperty(registrationForm, "password");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorPassword", constraintViolations.iterator().next().getMessage());
            addGlobalRegistrationErrorAttribute(request);
        }
    }

    private void checkEmail(HttpServletRequest request, RegistrationForm registrationForm) {
        Set<ConstraintViolation<RegistrationForm>> constraintViolations;
        constraintViolations = validator.validateProperty(registrationForm, "email");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorEmail", constraintViolations.iterator().next().getMessage());
            addGlobalRegistrationErrorAttribute(request);
        }
    }

    private void checkName(HttpServletRequest request, RegistrationForm registrationForm) {
        Set<ConstraintViolation<RegistrationForm>> constraintViolations;
        constraintViolations = validator.validateProperty(registrationForm, "name");
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errorName", constraintViolations.iterator().next().getMessage());
            addGlobalRegistrationErrorAttribute(request);
        }
    }

    private void addGlobalRegistrationErrorAttribute(HttpServletRequest request) {
        request.setAttribute("error", resourceBundle.getString("register.error.global"));
    }

}
