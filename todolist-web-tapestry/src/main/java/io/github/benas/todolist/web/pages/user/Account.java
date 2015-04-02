/*
 * The MIT License
 *
 * Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.benas.todolist.web.pages.user;

import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import io.github.todolist.core.service.api.UserService;
import io.github.benas.todolist.web.pages.Index;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Account {

    @Inject
    private UserService userService;

    @Inject
    private TodoService todoService;

    @Inject
    private Request request;

    @Inject
    private Messages messages;

    @Property
    @SessionState
    private User loggedUser;

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String email;

    @Property
    @Component
    private Form changePasswordForm;

    @Property
    @Component
    private Form updateAccountForm;

    @Property
    private String error;

    @Property
    private String currentPassword;

    @Property
    private String newPassword;

    @Property
    private String confirmPassword;

    @Property
    @Persist(value = PersistenceConstants.FLASH)
    private String updateProfileSuccessMessage;

    @Property
    @Persist(value = PersistenceConstants.FLASH)
    private String updatePasswordSuccessMessage;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void init() {
        firstName = loggedUser.getFirstName();
        lastName = loggedUser.getLastName();
        email = loggedUser.getEmail();
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "updateAccountForm")
    public void validateUpdateAccountForm() {
        if (userService.getUserByEmail(email) != null && !email.equals(loggedUser.getEmail())) {
            updateAccountForm.recordError(messages.format("account.email.alreadyUsed", email));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "updateAccountForm")
    public Object updateAccount() {
        loggedUser.setFirstName(firstName);
        loggedUser.setLastName(lastName);
        loggedUser.setEmail(email);
        userService.update(loggedUser);
        updateProfileSuccessMessage = messages.get("account.profile.update.success");
        return null;
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "changePasswordForm")
    public void validateChangePasswordForm() {
        if (!currentPassword.equals(loggedUser.getPassword())) {
            changePasswordForm.recordError(messages.get("account.password.error"));
        } else {
            if (!newPassword.equals(confirmPassword))
                changePasswordForm.recordError(messages.get("account.password.confirmation.error"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "changePasswordForm")
    public Object changePassword() {
        loggedUser.setPassword(newPassword);
        userService.update(loggedUser);
        updatePasswordSuccessMessage = messages.get("account.password.update.success");
        return null;
    }

    @OnEvent(value= EventConstants.ACTION,component="deleteAccountLink")
    public Object deleteAccount(){
        userService.remove(loggedUser);
        request.getSession(false).invalidate();
        loggedUser = null;
        return Index.class;
    }

}
