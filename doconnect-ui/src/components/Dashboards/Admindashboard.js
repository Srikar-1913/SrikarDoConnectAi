import { useState } from "react";
import Questions from "../Questions";
import AddQuestion from "../AddQuestion";
import Users from "../Users";
import "../../styles/dashboard.css";

// Admin Dashboard component
export default function AdminDashboard() {

  // State to manage current view (questions / add / users)
  const [view, setView] = useState(
    localStorage.getItem("view") || "questions"
  );

  // Logout function to clear storage and redirect to login page
  const logout = () => {
    localStorage.clear();
    window.location.href = "/";
  };

  return (
    <div className="dashboard-container">

      {/* SIDEBAR */}
      <div className="sidebar">

        {/* Dashboard logo/title */}
        <h4 className="logo">Admin Panel 👑</h4>

        {/* Navigate to all questions */}
        <button
          className={`menu-item ${view === "questions" ? "active" : ""}`}
          onClick={() => setView("questions")}
        >
          All Questions
        </button>

        {/* Navigate to add question page */}
        <button
          className={`menu-item ${view === "add" ? "active" : ""}`}
          onClick={() => setView("add")}
        >
          Add Question
        </button>

        {/* Navigate to users list */}
        <button
          className={`menu-item ${view === "users" ? "active" : ""}`}
          onClick={() => setView("users")}
        >
          Users
        </button>

        {/* Redirect to questions for delete operations */}
        <button
          className="menu-item"
          onClick={() => setView("questions")}
        >
          Delete
        </button>

        {/* Navigate to profile page */}
        <button
          className="menu-item"
          onClick={() => window.location.href = "/profile"}
        >
          Profile
        </button>

        {/* Logout button */}
        <button className="logout-btn" onClick={logout}>
          Logout
        </button>

      </div>

      {/* MAIN CONTENT */}
      <div className="main-content">

        {/* Top header section */}
        <div className="topbar">
          <h3>Admin Dashboard 🚀</h3>
          <p>Manage entire system from here</p>
        </div>

        {/* Statistics section */}
        <div className="stats-grid">

          {/* Total questions */}
          <div className="stat-card">
            <p>Total Questions</p>
            <h3>120</h3>
          </div>

          {/* Total answers */}
          <div className="stat-card">
            <p>Total Answers</p>
            <h3>98</h3>
          </div>

          {/* Total users */}
          <div className="stat-card">
            <p>Total Users</p>
            <h3>45</h3>
          </div>

          {/* Reports count */}
          <div className="stat-card">
            <p>Reports</p>
            <h3>5</h3>
          </div>

        </div>

        {/* Dynamic content section */}
        <div className="content-section">

          {/* Show Add Question component */}
          {view === "add" && <AddQuestion />}

          {/* Show Questions component */}
          {view === "questions" && <Questions />}

          {/* Show Users component */}
          {view === "users" && <Users />}

          {/* Delete info message */}
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