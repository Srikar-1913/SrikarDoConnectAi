import { useState, useEffect } from "react";
import Chat from "./Chat";
import API from "../services/api";

export default function ChatPage() {

  const [chatText, setChatText] = useState({});
  const [messages, setMessages] = useState([]);

  // ✅ Load messages from backend
  useEffect(() => {
    loadMessages();
  }, []);

  const loadMessages = async () => {
    try {
      const res = await API.get("/chatmessages/getAll");
      setMessages(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // ✅ Send message → store in backend
  const sendMessage = async (questionId) => {

    const textValue = chatText[questionId];

    if (!textValue || !textValue.trim()) return;

    try {
      await API.post("/chatmessages/save", {
        message: textValue,
        answerId: 1   // ✅ Replace later with dynamic id if needed
      });

      // ✅ Clear input box
      setChatText(prev => ({
        ...prev,
        [questionId]: ""
      }));

      // ✅ Reload messages from DB
      loadMessages();

    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h3>Chat Page</h3>

      <Chat
        messages={messages || []}
        sendMessage={sendMessage}
        text={chatText}
        setText={setChatText}
        questionId={1}
      />
    </div>
  );
}