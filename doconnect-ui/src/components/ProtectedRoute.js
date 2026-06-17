import { Navigate } from "react-router-dom";

// Component to protect routes from unauthorized access
export default function ProtectedRoute({ children }) {

  // Get JWT token from localStorage
  const token = localStorage.getItem("token");

  // If token is not present, redirect to login page
  if (!token) {
    return <Navigate to="/" />;
  }

  // If token exists, allow access to the requested component
  return children;
}