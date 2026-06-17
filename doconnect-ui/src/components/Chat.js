export default function Chat({ messages, sendMessage, text, setText, questionId }) {

  const userId = localStorage.getItem("userId");

  return (
    <div style={{ marginTop: "10px" }}>

      {/* CHAT BOX */}
      <div style={{
        maxHeight: "300px",
        overflowY: "auto",
        padding: "10px",
        border: "1px solid #ccc"
      }}>

        {messages?.map((m) => {
          const isMe = m.user?.userId == userId;

          return (
            <div
              key={m.messageId}
              style={{
                display: "flex",
                alignItems: "center",
                justifyContent: isMe ? "flex-end" : "flex-start",
                marginBottom: "10px"
              }}
            >

              {/* LEFT AVATAR */}
              {!isMe && (
                <div style={{
                  width: "30px",
                  height: "30px",
                  borderRadius: "50%",
                  background: "#6c63ff",
                  color: "white",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  fontSize: "12px",
                  marginRight: "6px"
                }}>
                  {m.user?.name?.charAt(0)}
                </div>
              )}

              {/* MESSAGE */}
              <div style={{
                maxWidth: "200px",
                padding: "6px 10px",
                borderRadius: "10px",
                background: isMe ? "#6c63ff" : "#eee",
                color: isMe ? "white" : "black",
                fontSize: "13px"
              }}>
                {m.message}

                <div style={{
                  fontSize: "10px",
                  marginTop: "3px",
                  opacity: 0.6
                }}>
                  {new Date(m.createdAt || Date.now()).toLocaleTimeString([], {
                    hour: "2-digit",
                    minute: "2-digit"
                  })}
                </div>
              </div>

              {/* RIGHT AVATAR */}
              {isMe && (
                <div style={{
                  width: "30px",
                  height: "30px",
                  borderRadius: "50%",
                  background: "#6c63ff",
                  color: "white",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  fontSize: "12px",
                  marginLeft: "6px"
                }}>
                  {m.user?.name?.charAt(0)}
                </div>
              )}

            </div>
          );
        })}

      </div>

      {/* INPUT */}
      <div style={{ display: "flex", marginTop: "10px" }}>

        <input
          placeholder="Type a message..."
          value={text?.[questionId] || ""}
          onChange={(e) =>
            setText(prev => ({
              ...prev,
              [questionId]: e.target.value
            }))
          }
          style={{
            flex: 1,
            padding: "8px",
            borderRadius: "6px",
            border: "1px solid #ccc"
          }}
        />

        <button
          onClick={() => sendMessage(questionId)}
          style={{
            marginLeft: "6px",
            padding: "8px 12px",
            background: "#6c63ff",
            color: "white",
            border: "none",
            borderRadius: "6px",
            cursor: "pointer"
          }}
        >
          Send
        </button>

      </div>

    </div>
  );
}