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

package io.github.todolist.core.service.api;

import io.github.todolist.core.domain.User;

/**
 * Business interface for user manager.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public interface UserService {

    /**
     * Get user by id.
     * @param id the user identifier
     * @return the user with identifier id or null if no such user
     */
    User getUserById(final long id);

    /**
     * Get user by email.
     * @param email the user email
     * @return the user with the given email or null if no such user
     */
    User getUserByEmail(final String email);

    /**
     * Check user's email and password.
     * @param email the user email
     * @param password the user password
     * @return true if user credentials are ok, false else
     */
    boolean login(final String email, final String password);

    /**
     * Create a user.
     * @param user the user to create
     * @return the created user
     */
    User create(final User user);

    /**
     * Update a user.
     * @param user the user to update.
     * @return the updated user
     */
    User update(User user);

    /**
     * Remove a user.
     * @param user the user to remove
     */
    void remove(final User user);
}
