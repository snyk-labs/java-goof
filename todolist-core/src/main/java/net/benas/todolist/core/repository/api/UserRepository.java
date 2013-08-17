package net.benas.todolist.core.repository.api;

import net.benas.todolist.core.domain.User;

/**
 * Interface for user repository.
 * @author benas (md.benhassine@gmail.com)
 */
public interface UserRepository {

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
