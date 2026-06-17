import { useEffect, useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import { getAIAnswer } from "../services/api";
import Chat from "./Chat";   // ✅ NEW

export default function Questions() {

  const [questions, setQuestions] = useState([]);
  const [answersMap, setAnswersMap] = useState({});
  const [counts, setCounts] = useState({});
  const [likedAnswers, setLikedAnswers] = useState({});

  const [messagesMap, setMessagesMap] = useState({});
  const [chatText, setChatText] = useState({});
  const [showChat, setShowChat] = useState({});

  const [aiAnswers, setAiAnswers] = useState({});


  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";


  const navigate = useNavigate();

  useEffect(() => {
    loadQuestions();
  }, []);

  const loadQuestions = async () => {
    try {
      const res = await API.get("/questions/getAll");
      setQuestions(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const toggleAnswers = async (questionId) => {
    try {
      const res = await API.get("/answers/getAll");

      console.log("ALL ANSWERS:", res.data);

      const filtered = res.data.filter(
        (a) => a.question && a.question.questionId === questionId
      );

      console.log("FILTERED:", filtered);

      setAnswersMap(prev => ({
        ...prev,
        [questionId]: filtered
      }));

      filtered.forEach(a => loadCounts(a.answerId));

    } catch (err) {
      console.error("VIEW ERROR:", err.response || err);
    }
  };

  // ✅ ✅ LOAD MESSAGES
  const loadMessages = async (questionId) => {
    try {
      const res = await API.get("/chatmessages/getAll");

      const answerIds = answersMap[questionId]?.map(a => a.answerId) || [];

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

  // ✅ ✅ CHAT TOGGLE
  const toggleChat = async (questionId) => {
    setShowChat(prev => ({
      ...prev,
      [questionId]: !prev[questionId]
    }));

    if (!messagesMap[questionId]) {
      await loadMessages(questionId);
    }
  };

  // ✅ ✅ SEND MESSAGE
  const sendMessage = async (questionId) => {
    try {
      const text = chatText[questionId];

      if (!text || !text.trim()) return;

      const firstAnswer = answersMap[questionId]?.[0];
      if (!firstAnswer) {
        alert("No answers available for chat");
        return;
      }

      await API.post("/chatmessages/save", {
        message: text,
        answerId: firstAnswer.answerId
      });

      setChatText(prev => ({
        ...prev,
        [questionId]: ""
      }));

      loadMessages(questionId);

    } catch (err) {
      console.error(err);
    }
  };

  const like = async (answerId) => {
    try {

      // ✅ User can like only once
      if (!isAdmin && likedAnswers[answerId]) {
        alert("You already liked this answer");
        return;
      }

      await API.post("/impressions", { answerId, type: "LIKE" });

      // ✅ track likes (no effect for admin)
      setLikedAnswers(prev => ({
        ...prev,
        [answerId]: true
      }));

      loadCounts(answerId);

    } catch (err) {
      console.error(err);
    }
  };

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

  // ✅ ADD HERE
  const deleteAnswer = async (answerId, questionId) => {
    try {
      const confirmDelete = window.confirm("Are you sure you want to delete this answer?");
      if (!confirmDelete) return;

      await API.delete(`/answers/delete/${answerId}`);

      // ✅ update UI instantly (correct way)
      setAnswersMap(prev => ({
        ...prev,
        [questionId]: prev[questionId].filter(a => a.answerId !== answerId)
      }));

    } catch (err) {
      console.error("Delete Answer Error:", err);
    }
  };

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


  // ✅ ✅ AI FUNCTION
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

      <h3 className="fw-bold mb-4">All Questions</h3>

      {questions.map(q => (

        <div key={q.questionId} className="question-card mb-4 p-4">

          {/* ✅ QUESTION */}
          <div className="d-flex justify-content-between align-items-start">

            <div>
              <h5 className="fw-bold">{q.title}</h5>
              <p className="text-muted">{q.description}</p>

              <small>
                Asked by {q.user?.name}
              </small>
            </div>

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

              {/* ✅ ONLY ADMIN CAN SEE */}
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

          {/* ✅ ANSWERS */}
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

          {/* ✅ CHAT COMPONENT */}
          {showChat[q.questionId] && (
            <Chat
              messages={messagesMap[q.questionId] || []}
              sendMessage={sendMessage}
              text={chatText}
              setText={setChatText}
              questionId={q.questionId}
            />
          )}

          {/* ✅ AI ANSWER */}
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