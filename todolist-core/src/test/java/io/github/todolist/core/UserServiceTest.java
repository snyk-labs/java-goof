package io.github.todolist.core;

import io.github.todolist.core.service.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/application-context.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testLogin() {
        assertTrue(userService.login("foo@bar.org", "foobar"));
    }

}
