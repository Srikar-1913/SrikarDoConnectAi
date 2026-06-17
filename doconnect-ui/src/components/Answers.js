import { useEffect, useState } from "react";
import API from "../services/api";
import { useParams } from "react-router-dom";

export default function Answers() {

  const { id } = useParams();
  const [answers, setAnswers] = useState([]);

  useEffect(() => {
    loadAnswers();
  }, [id]);

  const loadAnswers = async () => {
    try {
      const res = await API.get(`/answers/getAll`);
      console.log("Response:", res.data);
      setAnswers(res.data || []);
    } catch (err) {
      console.log("Error:", err);
      alert("Failed to load answers");
    }
  };

  return (
    <div className="container mt-5">
      <h2>Answers</h2>

      {answers.length === 0 ? (
        <p>No answers available</p>
      ) : (
        answers.map(a => (
          <div key={a.answerId} className="card p-3 mb-3">
            <p>{a.content}</p>
          </div>
        ))
      )}
    </div>
  );
}
