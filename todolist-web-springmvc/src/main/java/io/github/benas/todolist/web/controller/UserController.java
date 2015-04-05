/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.github.benas.todolist.web.controller;

import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import io.github.todolist.core.service.api.UserService;
import io.github.benas.todolist.web.common.form.ChangePasswordForm;
import io.github.benas.todolist.web.common.form.RegistrationForm;
import io.github.benas.todolist.web.util.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Controller for user account operations.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private MessageSource messageProvider;

    @Autowired
    private SessionData sessionData;

    /**********************
    * Registration Process
    **********************/

    @RequestMapping("/register")
    public ModelAndView redirectToRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("registerTabStyle", "active");
        modelAndView.addObject("registrationForm", new RegistrationForm());
        modelAndView.setViewName("user/register");
        return modelAndView;
    }

    @RequestMapping(value = "/register.do" , method = RequestMethod.POST)
    public String doRegister(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model) {

        final String view = "user/register";

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", messageProvider.getMessage("register.error.global", null, sessionData.getLocale()));
            return view;
        }

        if (newPasswordDoesNotMatchConfirmationPassword(registrationForm.getPassword(), registrationForm.getConfirmationPassword())) {
            model.addAttribute("error", messageProvider.getMessage("register.error.password.confirmation.error", null, sessionData.getLocale()));
            return view;
        }

        final String newEmail = registrationForm.getEmail();

        if (emailIsAlreadyUsed(newEmail)) {
            model.addAttribute("error", messageProvider.getMessage("register.error.global.account", new Object[]{newEmail}, sessionData.getLocale()));
            return view;
        }

        User user = new User();
        user.setName(registrationForm.getName());
        user.setEmail(newEmail);
        user.setPassword(registrationForm.getPassword());

        user = userService.create(user);
        sessionData.setUser(user);
        sessionData.setLocale(Locale.ENGLISH);

        return "redirect:/user/todos";
    }

    private boolean newPasswordDoesNotMatchConfirmationPassword(String newPassword, String confirmationPassword) {
        return !newPassword.equals(confirmationPassword);
    }

    private boolean emailIsAlreadyUsed(String email) {
        return userService.getUserByEmail(email) != null;
    }

    /**********************
    * Home page
    **********************/

    @RequestMapping("/user/todos")
    public ModelAndView loadTodoList() {

        ModelAndView modelAndView = new ModelAndView();
        // user login is ensured by the login filter/interceptor
        List<Todo> todoList = todoService.getTodoListByUser(sessionData.getUser().getId());
        modelAndView.addObject("todoList", todoList);
        int totalCount = todoList.size();
        int doneCount = todoService.getTodoListByStatus(sessionData.getUser().getId(), true).size();
        int todoCount = totalCount - doneCount;
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("doneCount", doneCount);
        modelAndView.addObject("todoCount", todoCount);
        modelAndView.addObject("homeTabStyle", "active");
        modelAndView.setViewName("user/home");
        return modelAndView;

    }

    /**********************
    * Account details page
    **********************/

    @RequestMapping("/user/account")
    public ModelAndView redirectToAccountPage() {
        ModelAndView modelAndView = new ModelAndView("user/account");
        final User user = sessionData.getUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("changePasswordForm", new ChangePasswordForm());
        return modelAndView;
    }

    /**********************
    * Delete Account
    **********************/

    @RequestMapping(value = "/user/account/delete.do", method = RequestMethod.POST)
    public String deleteAccount(HttpSession session) {
        userService.remove(sessionData.getUser());
        sessionData.setUser(null);
        session.invalidate();
        return "index";
    }

    /**********************
    * Change password
    **********************/

    @RequestMapping(value = "/user/account/password.do", method = RequestMethod.POST)
    public String changePassword(@Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, Model model) {
        User user = sessionData.getUser();
        String view = "user/account";

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return view;
        }
        if (newPasswordDoesNotMatchConfirmationPassword(changePasswordForm.getNewPassword(), changePasswordForm.getConfirmationPassword())) {
            model.addAttribute("error", messageProvider.getMessage("account.password.confirmation.error", null, sessionData.getLocale()));
            model.addAttribute("user", user);
            return view;
        }
        if (currentPasswordIsIncorrect(changePasswordForm, user)) {
            model.addAttribute("error", messageProvider.getMessage("account.password.error", null, sessionData.getLocale()));
            model.addAttribute("user", user);
            return view;
        }

        user.setPassword(changePasswordForm.getNewPassword());
        userService.update(user);
        model.addAttribute("updatePasswordSuccessMessage", messageProvider.getMessage("account.password.update.success", null, sessionData.getLocale()));
        model.addAttribute("user", user);
        return view;

    }

    private boolean currentPasswordIsIncorrect(ChangePasswordForm changePasswordForm, User user) {
        return !user.getPassword().equals(changePasswordForm.getCurrentPassword());
    }

    /*****************************
    * Update personal information
    *****************************/

    @RequestMapping(value = "/user/account/update.do", method = RequestMethod.POST)
    public String updatePersonalInformation(@RequestParam String name, @RequestParam String email, Model model) {
        User user = sessionData.getUser();

        if (emailIsAlreadyUsed(email) && isDifferent(email)) {
            model.addAttribute("error", messageProvider.getMessage("account.email.alreadyUsed", new Object[]{email}, sessionData.getLocale()));
        } else {
            user.setName(name);
            user.setEmail(email);
            userService.update(user);
            model.addAttribute("updateProfileSuccessMessage", messageProvider.getMessage("account.profile.update.success", null, sessionData.getLocale()));
            model.addAttribute("changePasswordForm", new ChangePasswordForm()); //needed since the update password form is on the same page
        }
        model.addAttribute("user", user);
        return "user/account";
    }

    private boolean isDifferent(String email) {
        return !email.equals(sessionData.getUser().getEmail());
    }

}
