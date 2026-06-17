
import UserDashboard from "./UserDashboard";
import AdminDashboard from "./Admindashboard";


export default function Dashboard() {
  const storedUser = localStorage.getItem("user");

  const user =
    storedUser && storedUser !== "undefined"
      ? JSON.parse(storedUser)
      : null;

  if (user?.role === "ADMIN") {
    return <AdminDashboard />;
  }

  return <UserDashboard />;
}
