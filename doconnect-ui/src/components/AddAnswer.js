import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../services/api";

// Component to add answer to a question
export default function AddAnswer() {

    // Get question ID from URL
    const { id } = useParams();

    // Navigation hook
    const navigate = useNavigate();

    // State to store answer content
    const [content, setContent] = useState("");

    // Function to submit answer
    const submitAnswer = async () => {

        try {

            // Call API to save answer
            await API.post("/answers/save", {
                content: content,
                questionId: Number(id),
                // userId can be taken from backend (JWT)
            });

            // Show success message
            alert("Answer added successfully");

            // Redirect to questions page
            navigate("/questions");

        } catch (err) {

            // Handle error
            console.error("Error:", err);
            alert("Failed to add answer");
        }
    };

    return (
        <div className="container mt-4">

            {/* Page title */}
            <h3>Add Answer</h3>

            {/* Input for answer content */}
            <textarea
                className="form-control mt-3"
                rows="4"
                placeholder="Type your answer here..."
                value={content}
                onChange={(e) => setContent(e.target.value)}
            />

            {/* Submit button */}
            <button
                className="btn btn-success mt-3"
                onClick={submitAnswer}
            >
                Submit Answer
            </button>

        </div>
    );
}