import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Questions from "../Questions";
import AddQuestion from "../AddQuestion";
import "../../styles/dashboard.css";

export default function Dashboard() {

  const navigate = useNavigate();
  const [view, setView] = useState("questions");

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="dashboard-container">

      {/* SIDEBAR */}
      <div className="sidebar">

        <h4 className="logo">DoConnect AI</h4>

        <button
          className={`menu-item ${view === "questions" ? "active" : ""}`}
          onClick={() => setView("questions")}
        >
          Dashboard
        </button>

        <button
          className={`menu-item ${view === "questions" ? "active" : ""}`}
          onClick={() => setView("questions")}
        >
          Questions
        </button>

        <button
          className={`menu-item ${view === "add" ? "active" : ""}`}
          onClick={() => setView("add")}
        >
          Ask Question
        </button>

        <button className="menu-item" onClick={() => navigate("/chat")}>
          Messages
        </button>

        <button className="menu-item" onClick={() => navigate("/profile")}>
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

          <div>
            <h3>
              {view === "add" ? "Ask a Question ✍️" : "Dashboard 👋"}
            </h3>

            <p>
              {view === "add"
                ? "Post your question to get answers from the community"
                : "Welcome back! Here's what's happening today."}
            </p>
          </div>

          {view !== "add" && (
            <input
              className="search-bar"
              placeholder="Search questions..."
            />
          )}

        </div>

        {/* ✅ SHOW STATS ONLY FOR DASHBOARD */}
        {view !== "add" && (
          <div className="stats-grid">

            <div className="stat-card">
              <p>Total Questions</p>
              <h3>120</h3>
            </div>

            <div className="stat-card">
              <p>Answers Available</p>
              <h3>98</h3>
            </div>

            <div className="stat-card">
              <p>Messages</p>
              <h3>46</h3>
            </div>

            <div className="stat-card">
              <p>Active Users</p>
              <h3>32</h3>
            </div>

          </div>
        )}

        {/* CONTENT */}
        <div className="content-section">

          {view === "add" && <AddQuestion />}
          {view === "questions" && <Questions />}

        </div>

      </div>

    </div>
  );
}
