import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/auth.css";

// Register component
export default function Register() {

  // Navigation hook
  const navigate = useNavigate();

  // State to store registration details
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    role: "USER"
  });

  // Loading state for button
  const [loading, setLoading] = useState(false);

  // Handle input field changes
  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value
    });
  };

  // Handle user registration
  const handleRegister = async () => {
    try {
      // Start loading
      setLoading(true);

      // Call API to register user
      await API.post("/users/register", user);

      // Success message
      alert("Registration successful");

      // Redirect to login page
      navigate("/");

    } catch (err) {

      // Handle error
      console.log(err.response?.data);
      alert(err.response?.data?.message || "Registration failed");

    } finally {
      // Stop loading
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">

      {/* LEFT SIDE */}
      <div className="auth-left">

        <h2>DoConnect AI</h2>

        {/* Heading */}
        <h1>Create account</h1>
        <p>Join DoConnect AI and start your AI-powered journey today.</p>

        {/* Features list */}
        <ul>
          <li>Ask, learn and get intelligent answers instantly</li>
          <li>Connect with your team and collaborate</li>
          <li>Secure, reliable and built for you</li>
        </ul>

      </div>

      {/* RIGHT SIDE */}
      <div className="auth-right">

        <div className="auth-card">

          {/* Form title */}
          <h3>Create your account</h3>
          <p className="sub-text">Fill in the details to get started</p>

          {/* Name and Email fields */}
          <div className="form-grid">

            <input
              name="name"
              placeholder="Full name"
              value={user.name}
              onChange={handleChange}
            />

            <input
              name="email"
              type="email"
              placeholder="Email address"
              value={user.email}
              onChange={handleChange}
            />

          </div>

          {/* Password and Role fields */}
          <div className="form-grid">

            <input
              name="password"
              type="password"
              placeholder="Password"
              value={user.password}
              onChange={handleChange}
            />

            {/* Role selection */}
            <select
              name="role"
              value={user.role}
              onChange={handleChange}
            >
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
            </select>

          </div>

          {/* Register button */}
          <button onClick={handleRegister} disabled={loading}>
            {loading ? "Registering..." : "Create Account"}
          </button>

          {/* Navigate to login */}
          <p className="switch-text">
            Already have an account?
            <span onClick={() => navigate("/")}>
              Login here
            </span>
          </p>

        </div>

      </div>
    </div>
  );
}