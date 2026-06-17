import { useEffect, useState } from "react";
import API from "../services/api";
import { useParams } from "react-router-dom";

// Component to display answers for a question
export default function Answers() {

  // Get question ID from URL
  const { id } = useParams();

  // State to store answers list
  const [answers, setAnswers] = useState([]);

  // Load answers when component mounts or id changes
  useEffect(() => {
    loadAnswers();
  }, [id]);

  // Fetch answers from backend
  const loadAnswers = async () => {
    try {
      const res = await API.get(`/answers/getAll`);
      console.log("Response:", res.data);

      // Set answers to state
      setAnswers(res.data || []);
    } catch (err) {
      console.log("Error:", err);

      // Error message
      alert("Failed to load answers");
    }
  };

  return (
    <div className="container mt-5">

      {/* Page title */}
      <h2>Answers</h2>

      {/* If no answers available */}
      {answers.length === 0 ? (
        <p>No answers available</p>
      ) : (

        // Display list of answers
        answers.map(a => (
          <div key={a.answerId} className="card p-3 mb-3">
            <p>{a.content}</p>
          </div>
        ))

      )}

    </div>
  );
}