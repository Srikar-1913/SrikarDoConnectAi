import { BrowserRouter, Routes, Route } from "react-router-dom";

// Import all components
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboards/Dashboard";
import Questions from "./components/Questions";
import AddQuestion from "./components/AddQuestion";
import Answers from "./components/Answers";
import AddAnswer from "./components/AddAnswer";
import Chat from "./components/Chat";
import ProtectedRoute from "./components/ProtectedRoute";
import chat from "./components/Chat";
import ChatPage from "./components/ChatPage";
import Profile from "./components/Profile";
import Users from "./components/Users";
import EditProfile from "./components/EditProfile";

// Main App component with routing setup
function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Public routes (no authentication required) */}
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Protected routes (require login) */}
        <Route
          path="/dashboard"
          element={<ProtectedRoute><Dashboard /></ProtectedRoute>}
        />

        <Route
          path="/questions"
          element={<ProtectedRoute><Questions /></ProtectedRoute>}
        />

        <Route
          path="/add-question"
          element={<ProtectedRoute><AddQuestion /></ProtectedRoute>}
        />

        <Route
          path="/answers/:id"
          element={<ProtectedRoute><Answers /></ProtectedRoute>}
        />

        <Route
          path="/add-answer/:id"
          element={<ProtectedRoute><AddAnswer /></ProtectedRoute>}
        />

        {/* Other pages */}
        <Route path="/chat" element={<ChatPage />} />

        <Route path="/profile" element={<Profile />} />

        <Route path="/users" element={<Users />} />

        <Route path="/edit-profile" element={<EditProfile />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;