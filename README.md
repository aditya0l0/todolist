# Simple Todo App — Spring Boot + React

A beginner-friendly todo list. The backend is 4 Java files. The frontend is 1 React file.

---

## How the pieces connect

```
Browser (React on :3000)
        │
        │  HTTP requests (GET / POST / PUT / DELETE)
        ▼
Spring Boot API (on :8080)
        │
        │  JPA / SQL queries
        ▼
H2 in-memory database
```

---

## Backend Setup

### Prerequisites
- Java 17+ installed → check with: `java -version`
- Maven installed → check with: `mvn -version`

### File structure
```
backend/
├── pom.xml
└── src/main/
    ├── java/com/example/todo/
    │   ├── TodoApplication.java   ← starts the app
    │   ├── Todo.java              ← the data model (maps to a DB table)
    │   ├── TodoRepository.java    ← talks to the database
    │   └── TodoController.java    ← handles HTTP requests
    └── resources/
        └── application.properties ← config (DB, JPA settings)
```

### Run the backend
```bash
cd backend
mvn spring-boot:run
```

You should see: `Started TodoApplication on port 8080`

### Test the API manually (optional)
Open a new terminal and try:
```bash
# Get all todos (empty at first)
curl http://localhost:8080/api/todos

# Create a todo
curl -X POST http://localhost:8080/api/todos \
     -H "Content-Type: application/json" \
     -d '{"title":"Buy groceries","done":false}'

# Get all todos again (should show your new one)
curl http://localhost:8080/api/todos
```

### View the H2 database console (optional)
1. Go to: http://localhost:8080/h2-console
2. JDBC URL: `jdbc:h2:mem:tododb`
3. Username: `sa`
4. Password: (leave blank)
5. Click Connect → you can run SQL queries and see your data live!

---

## Frontend Setup

### Prerequisites
- Node.js installed → check with: `node -v`

### File structure
```
frontend/
├── package.json
└── src/
    ├── App.jsx    ← the entire frontend (read the comments!)
    └── index.js
```

### Run the frontend
```bash
cd frontend
npx create-react-app .    # first time only — sets up the project
# Copy App.jsx into src/ replacing the default one
npm start
```

The app opens at: http://localhost:3000

---

## Key Concepts Explained

### Spring Boot Backend

| Annotation | What it does |
|---|---|
| `@SpringBootApplication` | Starts the whole app, enables auto-config |
| `@Entity` | Maps a Java class to a database table |
| `@Id` + `@GeneratedValue` | Auto-incrementing primary key |
| `@RestController` | Handles HTTP requests, returns JSON automatically |
| `@RequestMapping` | Sets the base URL path for the controller |
| `@GetMapping` | Handles GET requests |
| `@PostMapping` | Handles POST requests |
| `@PutMapping` | Handles PUT requests |
| `@DeleteMapping` | Handles DELETE requests |
| `@PathVariable` | Extracts `{id}` from the URL |
| `@RequestBody` | Converts JSON body → Java object |
| `@CrossOrigin` | Allows React (on port 3000) to call the API |
| `@Autowired` | Spring injects the dependency for you |
| `JpaRepository` | Gives you `findAll()`, `save()`, `deleteById()` for free |

### React Frontend

| Concept | What it does |
|---|---|
| `useState` | Stores data that triggers re-renders when it changes |
| `useEffect(fn, [])` | Runs `fn` once when the component first loads |
| `fetch()` | Makes HTTP requests to the backend |
| `async/await` | Waits for fetch to finish before continuing |
| `.map()` | Loops over an array and returns JSX for each item |
| `key={todo.id}` | Unique identifier React uses to track list items |

---

## What happens step by step when you add a todo

1. User types in the input box → `setInput(value)` updates React state
2. User clicks "Add" → `addTodo()` runs
3. `fetch(API, { method: "POST", body: ... })` sends the todo to Spring Boot
4. Spring Boot receives it in `@PostMapping create()` via `@RequestBody`
5. `repository.save(todo)` inserts a row into the H2 database
6. Spring Boot returns the saved todo (now with an ID) as JSON
7. `fetchTodos()` re-fetches the full list
8. `setTodos(data)` updates state → React re-renders the list

---

## Next steps when you're ready

- **Swap H2 for MySQL/PostgreSQL** — just change `application.properties` and add the driver dependency
- **Add a Service layer** — separates business logic from the controller
- **Add validation** — `@NotBlank`, `@Size` annotations on the entity
- **Add error handling** — `@ExceptionHandler` in the controller
