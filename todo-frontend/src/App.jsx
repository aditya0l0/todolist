/*
 * ============================================================
 *  REACT TODO FRONTEND
 * ============================================================
 *
 * This is the entire frontend in one file.
 * It talks to the Spring Boot API at http://localhost:8080/api/todos.
 *
 * React Concepts used here:
 *   - useState  : stores data that can change (like our list of todos)
 *   - useEffect : runs code when the component first loads
 *   - fetch     : the browser's built-in tool for making HTTP requests
 */

import { useState, useEffect } from "react";

// The URL of our Spring Boot API.
// We reference this constant instead of typing the URL every time.
const API = "http://localhost:8080/api/todos";

export default function App() {

  // -------------------------------------------------------
  //  STATE
  //  useState() creates a variable + a function to update it.
  //  When state changes, React re-renders the component.
  // -------------------------------------------------------

  // todos: the array of todo objects we got from the backend
  const [todos, setTodos] = useState([]);

  // input: what the user is currently typing in the text box
  const [input, setInput] = useState("");

  // -------------------------------------------------------
  //  LOAD TODOS ON STARTUP
  //  useEffect with an empty [] runs once when the page loads.
  //  It's the equivalent of "do this when the component mounts".
  // -------------------------------------------------------
  useEffect(() => {
    fetchTodos();
  }, []); // the [] means "only run this once, not on every render"

  // -------------------------------------------------------
  //  API FUNCTIONS
  //  Each function sends one HTTP request to the backend.
  // -------------------------------------------------------

  // GET /api/todos — load all todos from the database
  async function fetchTodos() {
    const response = await fetch(API);          // send the request
    const data     = await response.json();     // parse the JSON response
    setTodos(data);                             // update state → re-render
  }

  // POST /api/todos — create a new todo
  async function addTodo() {
    if (!input.trim()) return; // don't add empty todos

    await fetch(API, {
      method:  "POST",
      headers: { "Content-Type": "application/json" }, // tell the server we're sending JSON
      body:    JSON.stringify({ title: input, done: false }), // convert JS object → JSON string
    });

    setInput("");    // clear the text box
    fetchTodos();    // refresh the list
  }

  // PUT /api/todos/:id — toggle done/not-done
  async function toggleTodo(id) {
    await fetch(`${API}/${id}`, { method: "PUT" });
    fetchTodos(); // refresh after toggling
  }

  // DELETE /api/todos/:id — remove a todo
  async function deleteTodo(id) {
    await fetch(`${API}/${id}`, { method: "DELETE" });
    fetchTodos(); // refresh after deleting
  }

  // -------------------------------------------------------
  //  RENDER (the UI)
  //  JSX looks like HTML but it's actually JavaScript.
  //  Curly braces {} let you drop JS expressions inside JSX.
  // -------------------------------------------------------
  return (
    <div style={styles.page}>
      <div style={styles.card}>

        <h1 style={styles.heading}>Todo List</h1>

        {/* Input row */}
        <div style={styles.inputRow}>
          <input
            style={styles.input}
            value={input}
            onChange={(e) => setInput(e.target.value)}  // update state on every keystroke
            onKeyDown={(e) => e.key === "Enter" && addTodo()} // add on Enter key
            placeholder="What needs doing?"
          />
          <button style={styles.addBtn} onClick={addTodo}>
            Add
          </button>
        </div>

        {/* Todo list */}
        {todos.length === 0 ? (
          <p style={styles.empty}>Nothing here yet. Add your first todo!</p>
        ) : (
          <ul style={styles.list}>
            {/* .map() loops over the array and returns a JSX element for each item */}
            {todos.map((todo) => (
              <li key={todo.id} style={styles.item}>

                {/* Checkbox — clicking calls toggleTodo */}
                <input
                  type="checkbox"
                  checked={todo.done}
                  onChange={() => toggleTodo(todo.id)}
                  style={styles.checkbox}
                />

                {/* Title — strike through if done */}
                <span style={{ ...styles.title, textDecoration: todo.done ? "line-through" : "none", opacity: todo.done ? 0.45 : 1 }}>
                  {todo.title}
                </span>

                {/* Delete button */}
                <button style={styles.deleteBtn} onClick={() => deleteTodo(todo.id)}>
                  ✕
                </button>

              </li>
            ))}
          </ul>
        )}

        {/* Footer summary */}
        {todos.length > 0 && (
          <p style={styles.footer}>
            {todos.filter((t) => t.done).length} / {todos.length} completed
          </p>
        )}

      </div>
    </div>
  );
}

// -------------------------------------------------------
//  STYLES
//  Plain JS objects used as inline styles.
//  camelCase instead of kebab-case: backgroundColor not background-color
// -------------------------------------------------------
const styles = {
  page: {
    minHeight: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#f0f4f8",
    fontFamily: "'Segoe UI', sans-serif",
  },
  card: {
    backgroundColor: "#ffffff",
    borderRadius: "12px",
    padding: "2rem",
    width: "100%",
    maxWidth: "480px",
    boxShadow: "0 4px 24px rgba(0,0,0,0.08)",
  },
  heading: {
    margin: "0 0 1.5rem",
    fontSize: "1.6rem",
    fontWeight: 600,
    color: "#1a202c",
  },
  inputRow: {
    display: "flex",
    gap: "8px",
    marginBottom: "1.5rem",
  },
  input: {
    flex: 1,
    padding: "10px 14px",
    fontSize: "15px",
    border: "1.5px solid #e2e8f0",
    borderRadius: "8px",
    outline: "none",
  },
  addBtn: {
    padding: "10px 20px",
    backgroundColor: "#3b82f6",
    color: "#fff",
    border: "none",
    borderRadius: "8px",
    fontSize: "15px",
    cursor: "pointer",
  },
  list: {
    listStyle: "none",
    margin: 0,
    padding: 0,
    display: "flex",
    flexDirection: "column",
    gap: "10px",
  },
  item: {
    display: "flex",
    alignItems: "center",
    gap: "12px",
    padding: "12px 14px",
    backgroundColor: "#f8fafc",
    borderRadius: "8px",
    border: "1px solid #e2e8f0",
  },
  checkbox: {
    width: "18px",
    height: "18px",
    cursor: "pointer",
    flexShrink: 0,
  },
  title: {
    flex: 1,
    fontSize: "15px",
    color: "#2d3748",
    transition: "all 0.2s",
  },
  deleteBtn: {
    background: "none",
    border: "none",
    color: "#a0aec0",
    fontSize: "15px",
    cursor: "pointer",
    padding: "0 4px",
    flexShrink: 0,
  },
  empty: {
    textAlign: "center",
    color: "#a0aec0",
    fontSize: "14px",
    padding: "2rem 0",
  },
  footer: {
    textAlign: "right",
    color: "#a0aec0",
    fontSize: "13px",
    marginTop: "1rem",
    marginBottom: 0,
  },
};