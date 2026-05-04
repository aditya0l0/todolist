package com.projects.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * This class represents a single "todo" row in the database.
 *
 * KEY ANNOTATIONS:
 *
 *   @Entity  — Tells JPA: "Map this class to a database table."
 *              Spring will automatically create a table called "todo"
 *              with columns matching the fields below.
 *
 *   @Id      — Marks this field as the primary key (unique identifier for each row).
 *
 *   @GeneratedValue  — The database auto-increments the ID.
 *                      You don't set it manually; the DB assigns 1, 2, 3... automatically.
 *
 * NO SQL needed — JPA handles creating the table and columns for you!
 */
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       // Auto-generated: 1, 2, 3...

    private String title;  // The text of the todo (e.g. "Buy groceries")
    private boolean done;  // Has it been completed?

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    // Spring / JPA requires a no-argument constructor.
    public Todo() {}

    // -----------------------------------------------------------------------
    // Getters & Setters
    // -----------------------------------------------------------------------
    // JPA (and JSON serialization) uses these to read/write field values.

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
