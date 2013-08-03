package net.benas.todolist.core.test;

import net.benas.todolist.core.domain.Todo;
import net.benas.todolist.core.service.api.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/application-context.xml"})
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Test
    public void testGetTodoById() {
        Todo todo = todoService.getTodoById(1);
        assertNotNull(todo);
        assertEquals(1, todo.getId());
    }

}
