package net.benas.todolist.core.repository.api;

import net.benas.todolist.core.domain.User;

/**
 * Interface for user repository.
 * @author benas (md.benhassine@gmail.com)
 */
public interface UserRepository {

    User getUserById(final long id);

    User getUserByEmail(final String email);

    boolean login(final String email, final String password);

    User create(final User user);

    User update(User user);

    void remove(final User user);

}
