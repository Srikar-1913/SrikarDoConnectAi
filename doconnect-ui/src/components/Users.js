import { useEffect, useState } from "react";
import API from "../services/api";

export default function Users() {

  const [users, setUsers] = useState([]);

  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const res = await API.get("/users/all");
      setUsers(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const deleteUser = async (id) => {
    try {
      const confirmDelete = window.confirm("Are you sure you want to delete this user?");
      if (!confirmDelete) return;

      await API.delete(`/users/${id}`);
      loadUsers();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="container mt-4">

      <h3 className="fw-bold mb-4">All Users</h3>

      {users.map(u => (

        <div
          key={u.userId}
          className="border p-3 mb-3 rounded d-flex justify-content-between align-items-center"
        >

          {/* LEFT SIDE */}
          <div>
            <div><b>{u.name}</b></div>
            <div className="text-muted">{u.email}</div>
            <div className="text-muted">Role: {u.role}</div>
          </div>

          {/* ✅ DELETE BUTTON */}
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