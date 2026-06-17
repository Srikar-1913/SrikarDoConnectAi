import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Questions from "../Questions";
import AddQuestion from "../AddQuestion";
import "../../styles/dashboard.css";

// User Dashboard component
export default function Dashboard() {

  // Hook for navigation between pages
  const navigate = useNavigate();

  // State to control current view
  const [view, setView] = useState("questions");

  // Logout function to clear token and redirect
  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="dashboard-container">

      {/* SIDEBAR */}
      <div className="sidebar">

        {/* App title */}
        <h4 className="logo">DoConnect AI</h4>

        {/* Dashboard view */}
        <button
          className={`menu-item ${view === "questions" ? "active" : ""}`}
          onClick={() => setView("questions")}
        >
          Dashboard
        </button>

        {/* Questions view */}
        <button
          className={`menu-item ${view === "questions" ? "active" : ""}`}
          onClick={() => setView("questions")}
        >
          Questions
        </button>

        {/* Add question view */}
        <button
          className={`menu-item ${view === "add" ? "active" : ""}`}
          onClick={() => setView("add")}
        >
          Ask Question
        </button>

        {/* Navigate to chat page */}
        <button className="menu-item" onClick={() => navigate("/chat")}>
          Messages
        </button>

        {/* Navigate to profile */}
        <button className="menu-item" onClick={() => navigate("/profile")}>
          Profile
        </button>

        {/* Logout button */}
        <button className="logout-btn" onClick={logout}>
          Logout
        </button>

      </div>

      {/* MAIN CONTENT */}
      <div className="main-content">

        {/* TOP BAR */}
        <div className="topbar">

          <div>
            {/* Title changes based on view */}
            <h3>
              {view === "add" ? "Ask a Question ✍️" : "Dashboard 👋"}
            </h3>

            {/* Subtitle based on view */}
            <p>
              {view === "add"
                ? "Post your question to get answers from the community"
                : "Welcome back! Here's what's happening today."}
            </p>
          </div>

          {/* Search bar shown only in dashboard/questions view */}
          {view !== "add" && (
            <input
              className="search-bar"
              placeholder="Search questions..."
            />
          )}

        </div>

        {/* Stats section shown only when not adding question */}
        {view !== "add" && (
          <div className="stats-grid">

            {/* Total questions */}
            <div className="stat-card">
              <p>Total Questions</p>
              <h3>120</h3>
            </div>

            {/* Total answers */}
            <div className="stat-card">
              <p>Answers Available</p>
              <h3>98</h3>
            </div>

            {/* Messages count */}
            <div className="stat-card">
              <p>Messages</p>
              <h3>46</h3>
            </div>

            {/* Active users */}
            <div className="stat-card">
              <p>Active Users</p>
              <h3>32</h3>
            </div>

          </div>
        )}

        {/* CONTENT SECTION */}
        <div className="content-section">

          {/* Show Add Question component */}
          {view === "add" && <AddQuestion />}

          {/* Show Questions component */}
          {view === "questions" && <Questions />}

        </div>

      </div>

    </div>
  );
}