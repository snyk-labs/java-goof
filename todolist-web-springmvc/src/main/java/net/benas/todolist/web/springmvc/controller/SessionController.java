/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package net.benas.todolist.web.springmvc.controller;

import net.benas.todolist.core.service.api.UserService;
import net.benas.todolist.web.springmvc.form.LoginForm;
import net.benas.todolist.web.springmvc.util.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Controller for user session : login/logout process.
 * @author benas (md.benhassine@gmail.com)
 */
@Controller
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageProvider;

    @Autowired
    private SessionData sessionData;

    /*
    **********************
    * Login methods
    **********************
    */

    @RequestMapping("/login")
    public ModelAndView redirectToLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("loginTabStyle", "active");
        modelAndView.addObject("loginForm", new LoginForm());
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public ModelAndView doLogin(@Valid LoginForm loginForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("error", messageProvider.getMessage("login.error.global", null, sessionData.getLocale()));
            modelAndView.setViewName("user/login");
            return modelAndView;
        }

        if (!userService.login(loginForm.getEmail(), loginForm.getPassword())) {
            modelAndView.addObject("error", messageProvider.getMessage("login.error.global.invalid", null, sessionData.getLocale()));
            modelAndView.setViewName("user/login");
            return modelAndView;
        }

        sessionData.setUser(userService.getUserByEmail(loginForm.getEmail()));
        sessionData.setLocale(Locale.ENGLISH);
        modelAndView.setViewName("redirect:/user/todos");
        return modelAndView;
    }

    /*
    **********************
    * Logout methods
    **********************
    */

    @RequestMapping("/user/logout")
    public String logout(HttpSession session) {
        sessionData.setUser(null); //sessionData = null causes NPE in next login
        session.invalidate();
        return "index";
    }
}
