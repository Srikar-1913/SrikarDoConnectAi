import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/auth.css";

export default function Register() {

  const navigate = useNavigate();

  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    role: "USER"
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value
    });
  };

  const handleRegister = async () => {
    try {
      setLoading(true);

      await API.post("/users/register", user);

      alert("Registration successful");
      navigate("/");

    } catch (err) {
      console.log(err.response?.data);
      alert(err.response?.data?.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">

      {/* LEFT SIDE (same as login) */}
      <div className="auth-left">

        <h2>DoConnect AI</h2>

        <h1>Create account</h1>
        <p>Join DoConnect AI and start your AI-powered journey today.</p>

        <ul>
          <li>Ask, learn and get intelligent answers instantly</li>
          <li>Connect with your team and collaborate</li>
          <li>Secure, reliable and built for you</li>
        </ul>

      </div>

      {/* RIGHT SIDE */}
      <div className="auth-right">

        <div className="auth-card">

          <h3>Create your account</h3>
          <p className="sub-text">Fill in the details to get started</p>

          {/* TWO COLUMN GRID */}
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

          <div className="form-grid">

            <input
              name="password"
              type="password"
              placeholder="Password"
              value={user.password}
              onChange={handleChange}
            />

            <select
              name="role"
              value={user.role}
              onChange={handleChange}
            >
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
            </select>

          </div>

          <button onClick={handleRegister} disabled={loading}>
            {loading ? "Registering..." : "Create Account"}
          </button>

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