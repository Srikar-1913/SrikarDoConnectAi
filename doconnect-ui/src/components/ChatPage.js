import { useState, useEffect } from "react";
import Chat from "./Chat";
import API from "../services/api";

// Chat page component
export default function ChatPage() {

  // State to store typed messages for each question
  const [chatText, setChatText] = useState({});

  // State to store all chat messages
  const [messages, setMessages] = useState([]);

  // Load messages when page loads
  useEffect(() => {
    loadMessages();
  }, []);

  // Fetch messages from backend
  const loadMessages = async () => {
    try {
      const res = await API.get("/chatmessages/getAll");

      // Save messages to state
      setMessages(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Send message to backend
  const sendMessage = async (questionId) => {

    // Get current input text
    const textValue = chatText[questionId];

    // Prevent empty messages
    if (!textValue || !textValue.trim()) return;

    try {
      // Call API to store message
      await API.post("/chatmessages/save", {
        message: textValue,
        answerId: 1   // static id (can be dynamic later)
      });

      // Clear input after sending
      setChatText(prev => ({
        ...prev,
        [questionId]: ""
      }));

      // Reload messages
      loadMessages();

    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ padding: "20px" }}>

      {/* Page title */}
      <h3>Chat Page</h3>

      {/* Chat component with props */}
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
