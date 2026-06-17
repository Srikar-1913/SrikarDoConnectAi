import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/addQuestion.css";

// Component to add a new question
export default function AddQuestion() {

  // Hook for navigation
  const navigate = useNavigate();

  // State to store question details
  const [question, setQuestion] = useState({
    title: "",
    description: "",
    category: "General"
  });

  // Handle input changes
  const handleChange = (e) => {
    setQuestion({
      ...question,
      [e.target.name]: e.target.value
    });
  };

  // Submit question to backend
  const handleSubmit = async () => {
    try {

      // Validate description length
      if (question.description.length < 10) {
        alert("Description must contain at least 10 characters");
        return;
      }

      // Get user ID (optional if handled by backend)
      const userId = localStorage.getItem("userId");

      // Call API to save question
      await API.post("/questions/save", {
        title: question.title,
        description: question.description
      });

      // Success message
      alert("Question added successfully");

      // Redirect to questions page
      navigate("/questions");

    } catch (err) {

      // Error handling
      alert("Failed to add question");
    }
  };

  return (
    <div className="aq-container">

      {/* Page title */}
      <h3 className="aq-title">Ask a New Question</h3>

      {/* Subtitle */}
      <p className="aq-subtitle">
        Share your question and get answers from the community
      </p>

      <div className="aq-card">

        {/* Title and category input */}
        <div className="aq-row">

          <input
            name="title"
            value={question.title}
            onChange={handleChange}
            placeholder="Enter question title"
            className="aq-input"
          />

          <select
            name="category"
            value={question.category}
            onChange={handleChange}
            className="aq-select"
          >
            <option>General</option>
            <option>Technology</option>
            <option>Education</option>
          </select>

        </div>

        {/* Description input */}
        <textarea
          name="description"
          rows="5"
          value={question.description}
          onChange={handleChange}
          placeholder="Describe your question..."
          className="aq-textarea"
        />

        {/* Action buttons */}
        <div className="aq-actions">

          {/* Cancel and go back */}
          <button
            onClick={() => navigate("/questions")}
            className="aq-cancel"
          >
            Cancel
          </button>

          {/* Submit question */}
          <button
            onClick={handleSubmit}
            className="aq-submit"
          >
            Submit Question →
          </button>

        </div>

      </div>

    </div>
  );
}