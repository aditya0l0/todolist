package com.projects.todo;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * This interface handles ALL database operations for the Todo entity.
 *
 * How?  By extending JpaRepository<Todo, Long>, Spring automatically
 * generates a full database layer for us. We get these methods for FREE:
 *
 *   findAll()          → SELECT * FROM todo
 *   findById(id)       → SELECT * FROM todo WHERE id = ?
 *   save(todo)         → INSERT or UPDATE (if the todo already has an id, it updates)
 *   deleteById(id)     → DELETE FROM todo WHERE id = ?
 *   count()            → SELECT COUNT(*) FROM todo
 *   existsById(id)     → checks if a row with that id exists
 *
 * We don't write a single SQL query. Spring Data JPA does it all.
 *
 * JpaRepository<Todo, Long>
 *               ^^^^  ^^^^
 *               |     The type of the ID field (we used Long in Todo.java)
 *               The entity class this repository manages
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Nothing to add! We get all the basic database methods for free.
}