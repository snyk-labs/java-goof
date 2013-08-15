/*
 * The MIT License
 *
 * Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package net.benas.todolist.web.tapestry.pages.user.account;

import net.benas.todolist.core.domain.User;
import net.benas.todolist.core.service.api.UserService;
import net.benas.todolist.web.tapestry.pages.user.Home;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author benas (md.benhassine@gmail.com)
 */
@SuppressWarnings("unused")
public class Update {

    @Inject
    private UserService userService;

    @Property
    @SessionState
    private User loggedUser;

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String email;

    @OnEvent(value = EventConstants.SUCCESS, component = "updateAccountForm")
    public Object onSuccess() {
        loggedUser.setFirstname(firstName);
        loggedUser.setLastname(lastName);
        loggedUser.setEmail(email);
        userService.update(loggedUser);
        return Home.class;
    }


}
