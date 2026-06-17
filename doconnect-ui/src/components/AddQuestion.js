import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/addQuestion.css";

export default function AddQuestion() {

  const navigate = useNavigate();

  const [question, setQuestion] = useState({
    title: "",
    description: "",
    category: "General"
  });

  const handleChange = (e) => {
    setQuestion({
      ...question,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async () => {
    try {

      if (question.description.length < 10) {
        alert("Description must contain at least 10 characters");
        return;
      }

      const userId = localStorage.getItem("userId");

      await API.post("/questions/save", {
        title: question.title,
        description: question.description
      });


      alert("Question added successfully");
      navigate("/questions");

    } catch (err) {
      alert("Failed to add question");
    }
  };

  return (
    <div className="aq-container">

      <h3 className="aq-title">Ask a New Question</h3>
      <p className="aq-subtitle">
        Share your question and get answers from the community
      </p>

      <div className="aq-card">

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

        <textarea
          name="description"
          rows="5"
          value={question.description}
          onChange={handleChange}
          placeholder="Describe your question..."
          className="aq-textarea"
        />

        <div className="aq-actions">

          <button
            onClick={() => navigate("/questions")}
            className="aq-cancel"
          >
            Cancel
          </button>

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