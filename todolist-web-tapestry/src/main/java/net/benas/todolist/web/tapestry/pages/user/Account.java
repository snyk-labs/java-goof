package net.benas.todolist.web.tapestry.pages.user;

import net.benas.todolist.core.domain.User;
import net.benas.todolist.core.service.api.TodoService;
import net.benas.todolist.core.service.api.UserService;
import net.benas.todolist.web.tapestry.pages.Index;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import java.text.MessageFormat;

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
        firstName = loggedUser.getFirstname();
        lastName = loggedUser.getLastname();
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
        loggedUser.setFirstname(firstName);
        loggedUser.setLastname(lastName);
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
