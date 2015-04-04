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

package io.github.benas.todolist.web.components;

import io.github.benas.todolist.web.pages.About;
import io.github.benas.todolist.web.pages.Index;
import io.github.benas.todolist.web.pages.user.Home;
import io.github.benas.todolist.web.pages.user.Login;
import io.github.benas.todolist.web.pages.user.Register;
import io.github.todolist.core.domain.User;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.Request;

/**
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@SuppressWarnings("unused")
public class NavigationBar {

    @Inject
    private ComponentResources resources;

    @Property
    @SessionState
    private User loggedUser;

    @Inject
    private Request request;

    @Property
    private boolean loggedUserExists;

    public String getHomeTabStyle() {
        Component page = resources.getPage();
        return isHomePage(page) || isIndexPage(page) ? "active" : "";
    }

    public String getAboutTabStyle() {
        Component page = resources.getPage();
        return isAboutPage(page) ? "active" : "";
    }

    public String getRegisterTabStyle() {
        Component page = resources.getPage();
        return isRegisterPage(page) ? "active" : "";
    }

    public String getLoginTabStyle() {
        Component page = resources.getPage();
        return isLoginPage(page) ? "active" : "";
    }

    @OnEvent(value = EventConstants.ACTION, component = "logoutLink")
    public Object logout() {
        request.getSession(false).invalidate();
        loggedUser = null;
        return Index.class;
    }

    private boolean isIndexPage(Component page) {
        return page instanceof Index;
    }

    private boolean isHomePage(Component page) {
        return page instanceof Home;
    }

    private boolean isAboutPage(Component page) {
        return page instanceof About;
    }

    private boolean isRegisterPage(Component page) {
        return page instanceof Register;
    }

    private boolean isLoginPage(Component page) {
        return page instanceof Login;
    }

}
