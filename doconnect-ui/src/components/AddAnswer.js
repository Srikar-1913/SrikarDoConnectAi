import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../services/api";

export default function AddAnswer() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [content, setContent] = useState("");

    const submitAnswer = async () => {

        try {

            await API.post("/answers/save", {
                content: content,
                questionId: Number(id),
                // userId: Number(localStorage.getItem("userId")) || 1
            });

            alert("Answer added successfully");
            navigate("/questions");

        } catch (err) {
            console.error("Error:", err);
            alert("Failed to add answer");
        }
    };

    return (
        <div className="container mt-4">

            <h3>Add Answer</h3>

            <textarea
                className="form-control mt-3"
                rows="4"
                placeholder="Type your answer here..."
                value={content}
                onChange={(e) => setContent(e.target.value)}
            />

            <button
                className="btn btn-success mt-3"
                onClick={submitAnswer}
            >
                Submit Answer
            </button>

        </div>
    );
}