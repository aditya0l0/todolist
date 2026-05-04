package com.projects.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
 * This class is the REST API — it listens for HTTP requests from the React frontend
 * and sends back responses (as JSON).
 *
 * KEY ANNOTATIONS:
 *
 *   @RestController  — Two things in one:
 *                      1. Registers this class with Spring so it handles HTTP requests.
 *                      2. Every method automatically returns JSON (no extra config needed).
 *
 *   @RequestMapping("/api/todos")
 *                   — All routes in this class start with /api/todos.
 *                     So a GET on this class means: GET http://localhost:8080/api/todos
 *
 *   @CrossOrigin("http://localhost:5173")
 *                   — Allows the React app (running on port 5173) to call this API.
 *                     Without this, the browser blocks cross-origin requests (CORS policy).
 */
@RestController
@RequestMapping("/api/todos")
@CrossOrigin("http://localhost:5173")
public class TodoController {

    /*
     * @Autowired tells Spring: "Find the TodoRepository bean you created
     * and inject it here." We don't call 'new TodoRepository()' ourselves —
     * Spring manages the instance and hands it to us. This is called
     * Dependency Injection (DI).
     */
    @Autowired
    private TodoRepository repository;

    // -----------------------------------------------------------------------
    // GET /api/todos   →   Returns ALL todos as a JSON array
    // -----------------------------------------------------------------------
    /*
     * @GetMapping — This method runs when the frontend sends: GET /api/todos
     * The List<Todo> return value is automatically converted to JSON by Spring.
     *
     * Example response: [{"id":1,"title":"Buy milk","done":false}, ...]
     */
    @GetMapping
    public List<Todo> getAll() {
        return repository.findAll();
    }

    // -----------------------------------------------------------------------
    // POST /api/todos  →   Creates a new todo
    // -----------------------------------------------------------------------
    /*
     * @PostMapping — Runs on: POST /api/todos
     *
     * @RequestBody Todo todo
     *   — Spring reads the JSON body sent by React and converts it
     *     into a Todo Java object automatically.
     *
     *   React sends:  { "title": "Buy milk" }
     *   Spring gives us a Todo with title = "Buy milk"
     *
     * repository.save(todo) inserts it into the database and returns the
     * saved object (now with an auto-generated ID).
     */
    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    // -----------------------------------------------------------------------
    // PUT /api/todos/{id}  →   Toggles a todo's "done" status
    // -----------------------------------------------------------------------
    /*
     * @PutMapping("/{id}") — Runs on: PUT /api/todos/1, PUT /api/todos/2, etc.
     *
     * @PathVariable Long id
     *   — Extracts the number from the URL. For PUT /api/todos/3, id = 3.
     *
     * How the toggle works:
     *   1. Find the existing todo in the database by id.
     *   2. Flip its "done" field (true → false, false → true).
     *   3. Save it back.
     */
    @PutMapping("/{id}")
    public Todo toggle(@PathVariable Long id) {
        // findById returns an Optional<Todo> (it might not exist).
        // orElseThrow throws an error if no todo with that id is found.
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found: " + id));
        todo.setDone(!todo.isDone()); // flip the done flag
        return repository.save(todo); // save and return updated todo
    }

    // -----------------------------------------------------------------------
    // DELETE /api/todos/{id}  →   Deletes a todo
    // -----------------------------------------------------------------------
    /*
     * @DeleteMapping("/{id}") — Runs on: DELETE /api/todos/1, etc.
     *
     * void return type — we don't return anything.
     * The frontend just needs to know the request succeeded (HTTP 200 OK).
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}