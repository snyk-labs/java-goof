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

package net.benas.todolist.web.springmvc.form;

import org.hibernate.validator.constraints.impl.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for registration form.
 * @author benas (md.benhassine@gmail.com)
 */
public class RegistrationFormValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegistrationForm registrationForm = (RegistrationForm)target;

        //required values validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "register.error.firstname.required", "First name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "register.error.lastname.required", "Last name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "register.error.email.required", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "register.error.password.required", "Password is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword", "register.error.confirmationPassword.required", "Confirm password is required");

        //email format validation (using hibernate validator impl)
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.isValid(registrationForm.getEmail().trim(),null)) {
            errors.rejectValue("email", "register.error.email.invalid","Email has an invalid format");
        }

        //passwords size and match validation
        if (registrationForm.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue("password", "register.error.password.size", new Object[]{MINIMUM_PASSWORD_LENGTH}, "Password must have at least " + MINIMUM_PASSWORD_LENGTH + " characters");
        }
        if (registrationForm.getConfirmationPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue("confirmationPassword", "register.error.confirmationPassword.size", new Object[]{MINIMUM_PASSWORD_LENGTH}, "Confirm password must have at least " + MINIMUM_PASSWORD_LENGTH + " characters");
        }
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmationPassword())) {
            errors.rejectValue("confirmationPassword", "register.error.confirmationPassword.match", "Confirm password does not match password");
        }

    }

}
