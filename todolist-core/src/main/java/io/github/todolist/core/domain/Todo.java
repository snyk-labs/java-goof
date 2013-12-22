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

package io.github.todolist.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Todo entity.
 * @author benas (md.benhassine@gmail.com)
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "findTodosByUser", query = "SELECT t FROM Todo t where t.userId = ?1 order by t.dueDate"),
        @NamedQuery(name = "findTodosByStatus", query = "SELECT t FROM Todo t where t.userId = ?1 and t.status = ?2 order by t.dueDate"),
        @NamedQuery(name = "findTodosByPriority", query = "SELECT t FROM Todo t where t.userId = ?1 and t.priority = ?2 order by t.dueDate"),
        @NamedQuery(name = "findTodosByStatusAndPriority", query = "SELECT t FROM Todo t where t.userId = ?1 and t.status = ?2 and t.priority = ?3 order by t.dueDate"),
        @NamedQuery(name = "searchTodoListByTitleByUserId", query = "SELECT t FROM Todo t where t.userId = ?1 and upper(t.title) like ?2 order by t.dueDate")
})
public class Todo implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private long userId;

    @Column(length = 512)
    private String title;

    @Enumerated(value = EnumType.ORDINAL)
    private Status status;

    @Enumerated(value = EnumType.ORDINAL)
    private Priority priority;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public Todo() {
        status = Status.TODO;
        priority = Priority.LOW;
    }

    public Todo(long userId, String title, Status status, Priority priority, Date dueDate) {
        this.userId = userId;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Todo{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", status=").append(status);
        sb.append(", priority=").append(priority);
        sb.append(", dueDate=").append(dueDate);
        sb.append('}');
        return sb.toString();
    }
}
