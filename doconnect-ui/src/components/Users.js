import { useEffect, useState } from "react";
import API from "../services/api";

// Users component to display all users
export default function Users() {

  // State to store users list
  const [users, setUsers] = useState([]);

  // Get role from localStorage
  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";

  // Load users on component mount
  useEffect(() => {
    loadUsers();
  }, []);

  // Fetch all users from backend
  const loadUsers = async () => {
    try {
      const res = await API.get("/users/all");

      // Store users in state
      setUsers(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Delete user (admin only)
  const deleteUser = async (id) => {
    try {
      // Confirm before deletion
      const confirmDelete = window.confirm("Are you sure you want to delete this user?");
      if (!confirmDelete) return;

      // Call delete API
      await API.delete(`/users/${id}`);

      // Reload users list
      loadUsers();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="container mt-4">

      {/* Page title */}
      <h3 className="fw-bold mb-4">All Users</h3>

      {/* Loop through users */}
      {users.map(u => (

        <div
          key={u.userId}
          className="border p-3 mb-3 rounded d-flex justify-content-between align-items-center"
        >

          {/* LEFT SIDE: User details */}
          <div>
            <div><b>{u.name}</b></div>
            <div className="text-muted">{u.email}</div>
            <div className="text-muted">Role: {u.role}</div>
          </div>

          {/* DELETE button (admin only, cannot delete admin user) */}
          {isAdmin && u.role !== "ADMIN" && (
            <button
              className="btn btn-danger btn-sm"
              onClick={() => deleteUser(u.userId)}
            >
              Delete
            </button>
          )}

        </div>

      ))}

    </div>
  );
}