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

import io.github.todolist.core.domain.Status;
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

    /*
    **********************
    * Registration Process
    **********************
    */
    @RequestMapping("/register")
    public ModelAndView redirectToRegister() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("registerTabStyle", "active");
        modelAndView.addObject("registrationForm", new RegistrationForm());
        modelAndView.setViewName("user/register");
        return modelAndView;
    }

    @RequestMapping(value = "/register.do" , method = RequestMethod.POST)
    public String register(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", messageProvider.getMessage("register.error.global", null, sessionData.getLocale()));
            return "user/register";
        }

        if (!registrationForm.getPassword().equals(registrationForm.getConfirmationPassword())) {
            model.addAttribute("error", messageProvider.getMessage("register.error.password.confirmation.error", null, sessionData.getLocale()));
            return "user/register";
        }

        if (userService.getUserByEmail(registrationForm.getEmail()) != null) {
            model.addAttribute("error", messageProvider.getMessage("register.error.global.account", new Object[]{registrationForm.getEmail()}, sessionData.getLocale()));
            return "user/register";
        }

        User user = new User();
        user.setFirstName(registrationForm.getFirstname());
        user.setLastName(registrationForm.getLastname());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());

        user = userService.create(user);
        sessionData.setUser(user);
        sessionData.setLocale(Locale.ENGLISH);

        return "redirect:/user/todos";
    }

    /*
    **********************
    * Home page
    **********************
    */

    @RequestMapping("/user/todos")
    public ModelAndView loadTodo() {

        ModelAndView modelAndView = new ModelAndView();
        // user login ensured by login filter/interceptor
        List<Todo> todoList = todoService.getTodoListByUser(sessionData.getUser().getId());
        modelAndView.addObject("todoList", todoList);
        modelAndView.addObject("todoCount", todoService.getTodoListByStatus(sessionData.getUser().getId(), Status.TODO).size());
        modelAndView.addObject("doneCount", todoService.getTodoListByStatus(sessionData.getUser().getId(), Status.DONE).size());
        modelAndView.addObject("totalCount", (todoService.getTodoListByUser(sessionData.getUser().getId()).size()));
        modelAndView.addObject("homeTabStyle", "active");
        modelAndView.setViewName("user/home");
        return modelAndView;

    }

    /*
    **********************
    * Account details page
    **********************
    */

    @RequestMapping("/user/account")
    public ModelAndView redirectToAccountPage() {
        ModelAndView modelAndView = new ModelAndView("user/account");
        final User user = sessionData.getUser();
        modelAndView.addObject("user", user);
        modelAndView.addObject("changePasswordForm", new ChangePasswordForm());
        return modelAndView;
    }

    /*
    **********************
    * Delete Account
    **********************
    */

    @RequestMapping(value = "/user/account/delete.do", method = RequestMethod.POST)
    public String deleteAccount(HttpSession session) {
        userService.remove(sessionData.getUser());
        sessionData.setUser(null);
        session.invalidate();
        return "index";
    }

    /*
    **********************
    * Change password
    **********************
    */

    @RequestMapping(value = "/user/account/password.do", method = RequestMethod.POST)
    public String changePassword(@Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, Model model) {
        User user = sessionData.getUser();
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user/account";
        }
        if (!changePasswordForm.getPassword().equals(changePasswordForm.getConfirmPassword())) {
            model.addAttribute("error", messageProvider.getMessage("account.password.confirmation.error", null, sessionData.getLocale()));
            model.addAttribute("user", user);
            return "user/account";
        }
        if (!user.getPassword().equals(changePasswordForm.getCurrentPassword())) {
            model.addAttribute("error", messageProvider.getMessage("account.password.error", null, sessionData.getLocale()));
            model.addAttribute("user", user);
            return "user/account";
        } else { // validation ok
            user.setPassword(changePasswordForm.getPassword());
            userService.update(user);
            model.addAttribute("updatePasswordSuccessMessage", messageProvider.getMessage("account.password.update.success", null, sessionData.getLocale()));
            model.addAttribute("user", user);
            return "user/account";
        }
    }

    /*
    *****************************
    * Update personal information
    *****************************
    */

    @RequestMapping(value = "/user/account/update.do", method = RequestMethod.POST)
    public String updatePersonalInformation(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, Model model) {
        User user = sessionData.getUser();

        if (userService.getUserByEmail(email) != null && !email.equals(user.getEmail())) {
            model.addAttribute("error", messageProvider.getMessage("account.email.alreadyUsed", new Object[]{email}, sessionData.getLocale()));
        } else { // validation ok
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setEmail(email);
            userService.update(user);
            model.addAttribute("updateProfileSuccessMessage", messageProvider.getMessage("account.profile.update.success", null, sessionData.getLocale()));
            model.addAttribute("changePasswordForm", new ChangePasswordForm()); //needed since the update password form is on the same page
        }
        model.addAttribute("user", user);
        return "user/account";
    }

}
