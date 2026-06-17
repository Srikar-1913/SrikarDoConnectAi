import UserDashboard from "./UserDashboard";
import AdminDashboard from "./Admindashboard";

// Main dashboard component to decide view based on role
export default function Dashboard() {

  // Get user data from localStorage
  const storedUser = localStorage.getItem("user");

  // Parse user if available, else null
  const user =
    storedUser && storedUser !== "undefined"
      ? JSON.parse(storedUser)
      : null;

  // If user is admin, show AdminDashboard
  if (user?.role === "ADMIN") {
    return <AdminDashboard />;
  }

  // Otherwise, show UserDashboard
  return <UserDashboard />;
}