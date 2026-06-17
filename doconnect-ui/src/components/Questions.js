import { useEffect, useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import { getAIAnswer } from "../services/api";
import Chat from "./Chat";   // Chat component

export default function Questions() {

  // State to store questions
  const [questions, setQuestions] = useState([]);

  // Store answers mapped by questionId
  const [answersMap, setAnswersMap] = useState({});

  // Store like/dislike counts
  const [counts, setCounts] = useState({});

  // Track liked answers (to prevent multiple likes)
  const [likedAnswers, setLikedAnswers] = useState({});

  // Chat related states
  const [messagesMap, setMessagesMap] = useState({});
  const [chatText, setChatText] = useState({});
  const [showChat, setShowChat] = useState({});

  // Store AI generated answers
  const [aiAnswers, setAiAnswers] = useState({});

  // Get role from storage
  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";

  const navigate = useNavigate();

  // Load questions on page load
  useEffect(() => {
    loadQuestions();
  }, []);

  // Fetch all questions
  const loadQuestions = async () => {
    try {
      const res = await API.get("/questions/getAll");
      setQuestions(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Load answers for a specific question
  const toggleAnswers = async (questionId) => {
    try {
      const res = await API.get("/answers/getAll");

      // Filter answers belonging to selected question
      const filtered = res.data.filter(
        (a) => a.question && a.question.questionId === questionId
      );

      // Store filtered answers
      setAnswersMap(prev => ({
        ...prev,
        [questionId]: filtered
      }));

      // Load like/dislike counts
      filtered.forEach(a => loadCounts(a.answerId));

    } catch (err) {
      console.error("VIEW ERROR:", err.response || err);
    }
  };

  // Load messages for a question
  const loadMessages = async (questionId) => {
    try {
      const res = await API.get("/chatmessages/getAll");

      const answerIds = answersMap[questionId]?.map(a => a.answerId) || [];

      // Filter messages for related answers
      const filtered = res.data.filter(
        (m) => answerIds.includes(m.answer.answerId)
      );

      setMessagesMap(prev => ({
        ...prev,
        [questionId]: filtered
      }));

    } catch (err) {
      console.error(err);
    }
  };

  // Show/hide chat
  const toggleChat = async (questionId) => {
    setShowChat(prev => ({
      ...prev,
      [questionId]: !prev[questionId]
    }));

    // Load messages if not already loaded
    if (!messagesMap[questionId]) {
      await loadMessages(questionId);
    }
  };

  // Send message in chat
  const sendMessage = async (questionId) => {
    try {
      const text = chatText[questionId];

      // Prevent empty message
      if (!text || !text.trim()) return;

      const firstAnswer = answersMap[questionId]?.[0];

      // If no answer exists
      if (!firstAnswer) {
        alert("No answers available for chat");
        return;
      }

      // Save message
      await API.post("/chatmessages/save", {
        message: text,
        answerId: firstAnswer.answerId
      });

      // Clear input
      setChatText(prev => ({
        ...prev,
        [questionId]: ""
      }));

      // Reload messages
      loadMessages(questionId);

    } catch (err) {
      console.error(err);
    }
  };

  // Like answer
  const like = async (answerId) => {
    try {

      // Prevent multiple likes by user
      if (!isAdmin && likedAnswers[answerId]) {
        alert("You already liked this answer");
        return;
      }

      await API.post("/impressions", { answerId, type: "LIKE" });

      setLikedAnswers(prev => ({
        ...prev,
        [answerId]: true
      }));

      loadCounts(answerId);

    } catch (err) {
      console.error(err);
    }
  };

  // Dislike answer
  const dislike = async (answerId) => {
    try {

      if (!isAdmin && likedAnswers[answerId]) {
        alert("You already reacted");
        return;
      }

      await API.post("/impressions", { answerId, type: "DISLIKE" });

      setLikedAnswers(prev => ({
        ...prev,
        [answerId]: true
      }));

      loadCounts(answerId);

    } catch (err) {
      console.error(err);
    }
  };

  // Load like/dislike counts
  const loadCounts = async (answerId) => {
    try {
      const res = await API.get(`/impressions/count/${answerId}`);

      setCounts(prev => ({
        ...prev,
        [answerId]: res.data
      }));

    } catch (err) {
      console.warn("Counts not accessible for user");
    }
  };

  // Delete answer (admin only)
  const deleteAnswer = async (answerId, questionId) => {
    try {
      const confirmDelete = window.confirm("Are you sure you want to delete this answer?");
      if (!confirmDelete) return;

      await API.delete(`/answers/delete/${answerId}`);

      // Update UI after delete
      setAnswersMap(prev => ({
        ...prev,
        [questionId]: prev[questionId].filter(a => a.answerId !== answerId)
      }));

    } catch (err) {
      console.error("Delete Answer Error:", err);
    }
  };

  // Delete question (admin only)
  const deleteQuestion = async (id) => {
    try {
      const confirmDelete = window.confirm("Are you sure you want to delete this question?");
      if (!confirmDelete) return;

      await API.delete(`/questions/delete/${id}`);
      loadQuestions();

    } catch (err) {
      console.error(err);
    }
  };

  // Generate AI answer
  const generateAIAnswer = async (question) => {
    try {
      const res = await getAIAnswer({
        title: question.title,
        description: question.description
      });

      setAiAnswers(prev => ({
        ...prev,
        [question.questionId]: res.data
      }));

    } catch (err) {
      console.error(err);
      alert("AI response failed");
    }
  };

  return (
    <div className="container mt-4">

      {/* Page title */}
      <h3 className="fw-bold mb-4">All Questions</h3>

      {questions.map(q => (

        <div key={q.questionId} className="question-card mb-4 p-4">

          {/* QUESTION */}
          <div className="d-flex justify-content-between align-items-start">

            <div>
              <h5 className="fw-bold">{q.title}</h5>
              <p className="text-muted">{q.description}</p>
              <small>Asked by {q.user?.name}</small>
            </div>

            {/* Action buttons */}
            <div className="d-flex gap-2">

              <button
                className="btn btn-success btn-sm"
                onClick={() => navigate(`/add-answer/${q.questionId}`)}
              >
                Answer
              </button>

              <button
                className="btn btn-primary btn-sm"
                onClick={() => toggleAnswers(q.questionId)}
              >
                View
              </button>

              <button
                className="btn btn-dark btn-sm"
                onClick={() => toggleChat(q.questionId)}
              >
                Chat
              </button>

              <button
                className="btn btn-warning btn-sm"
                onClick={() => generateAIAnswer(q)}
              >
                AI Answer
              </button>

              {/* Admin only delete */}
              {isAdmin && (
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => deleteQuestion(q.questionId)}
                >
                  Delete
                </button>
              )}

            </div>
          </div>

          {/* ANSWERS */}
          {answersMap[q.questionId] && (
            <div className="mt-3">

              {answersMap[q.questionId].map(a => (

                <div
                  key={a.answerId}
                  className="answer-box p-2 mt-2 d-flex justify-content-between"
                >

                  <div>
                    <div>{a.content}</div>
                    <small className="text-muted">
                      Answered by {a.user?.name}
                    </small>
                  </div>

                  {/* Like / Dislike / Delete */}
                  <div>

                    <button
                      className="btn btn-light btn-sm me-2"
                      onClick={() => like(a.answerId)}
                    >
                      👍 {counts[a.answerId]?.likes || 0}
                    </button>

                    <button
                      className="btn btn-light btn-sm"
                      onClick={() => dislike(a.answerId)}
                    >
                      👎 {counts[a.answerId]?.dislikes || 0}
                    </button>

                    {isAdmin && (
                      <button
                        className="btn btn-danger btn-sm ms-2"
                        onClick={() => deleteAnswer(a.answerId, q.questionId)}
                      >
                        Delete
                      </button>
                    )}

                  </div>

                </div>
              ))}

            </div>
          )}

          {/* CHAT */}
          {showChat[q.questionId] && (
            <Chat
              messages={messagesMap[q.questionId] || []}
              sendMessage={sendMessage}
              text={chatText}
              setText={setChatText}
              questionId={q.questionId}
            />
          )}

          {/* AI ANSWER */}
          {aiAnswers[q.questionId] && (
            <div className="mt-3 p-3 bg-light border rounded">
              <strong>AI Answer:</strong>
              <div>{aiAnswers[q.questionId]}</div>
            </div>
          )}

        </div>
      ))}

    </div>
  );
}