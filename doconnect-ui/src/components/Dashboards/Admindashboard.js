import { useState } from "react";
import Questions from "../Questions";
import AddQuestion from "../AddQuestion";
import Users from "../Users";
import "../../styles/dashboard.css";

export default function AdminDashboard() {


  const [view, setView] = useState(
    localStorage.getItem("view") || "questions"
  );


  const logout = () => {
    localStorage.clear();
    window.location.href = "/";
  };

  return (
    <div className="dashboard-container">

      {/* SIDEBAR */}
      <div className="sidebar">

        <h4 className="logo">Admin Panel 👑</h4>

        <button
          className={`menu-item ${view === "questions" ? "active" : ""}`}
          onClick={() => setView("questions")}
        >
          All Questions
        </button>

        <button
          className={`menu-item ${view === "add" ? "active" : ""}`}
          onClick={() => setView("add")}
        >
          Add Question
        </button>

        {/* ✅ USERS BUTTON (always visible now) */}
        <button
          className={`menu-item ${view === "users" ? "active" : ""}`}
          onClick={() => setView("users")}
        >
          Users
        </button>

        {/* ✅ DELETE (optional - leave it) */}
        <button
          className="menu-item"
          onClick={() => setView("questions")}
        >
          Delete
        </button>

        <button
          className="menu-item"
          onClick={() => window.location.href = "/profile"}
        >
          Profile
        </button>

        <button className="logout-btn" onClick={logout}>
          Logout
        </button>

      </div>

      {/* MAIN CONTENT */}
      <div className="main-content">

        {/* TOP BAR */}
        <div className="topbar">
          <h3>Admin Dashboard 🚀</h3>
          <p>Manage entire system from here</p>
        </div>

        {/* STATS */}
        <div className="stats-grid">

          <div className="stat-card">
            <p>Total Questions</p>
            <h3>120</h3>
          </div>

          <div className="stat-card">
            <p>Total Answers</p>
            <h3>98</h3>
          </div>

          <div className="stat-card">
            <p>Total Users</p>
            <h3>45</h3>
          </div>

          <div className="stat-card">
            <p>Reports</p>
            <h3>5</h3>
          </div>

        </div>

        {/* CONTENT */}
        <div className="content-section">

          {view === "add" && <AddQuestion />}
          {view === "questions" && <Questions />}
          {view === "users" && <Users />}

          {view === "delete" && (
            <div>
              <h4>Use delete buttons in Questions section</h4>
            </div>
          )}

        </div>

      </div>
    </div>
  );
}
