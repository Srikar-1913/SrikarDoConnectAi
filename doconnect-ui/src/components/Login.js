import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import API from "../services/api";
import "../styles/auth.css";

export default function Login() {

  const navigate = useNavigate();

  const [user, setUser] = useState({
    email: "",
    password: ""
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value
    });
  };

  const handleLogin = async () => {
    try {

      setLoading(true);

      const res = await API.post("/users/login", user);

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", res.data.role);
      localStorage.setItem("email", res.data.email);
      localStorage.setItem("userId", res.data.userId); 
      // ✅ Decode token
      const decoded = jwtDecode(res.data.token);

      // ✅ Create user object manually
      const userData = {
        name: decoded.sub?.split("@")[0] || "User",
        email: decoded.sub,
        role: decoded.role || "USER"
      };

      // ✅ Store user
      localStorage.setItem("user", JSON.stringify(userData));


      alert("Login successful");

      navigate("/dashboard");

    } catch (err) {

      console.log(err.response?.data);
      alert(err.response?.data?.message || "Login failed");

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">

      {/* LEFT SIDE */}
      <div className="auth-left">

        <h2>DoConnect AI</h2>

        <h1>Welcome back!</h1>
        <p>Login to your account and continue your AI-powered journey.</p>

        <ul>
          <li>Ask, learn and get intelligent answers instantly</li>
          <li>Connect with your team and collaborate</li>
          <li>Secure, reliable and built for you</li>
        </ul>

      </div>

      {/* RIGHT SIDE */}
      <div className="auth-right">

        <div className="auth-card">

          <h3>Login to your account</h3>
          <p className="sub-text">Enter your credentials to access your account</p>

          <input
            name="email"
            type="email"
            placeholder="Email address"
            value={user.email}
            onChange={handleChange}
          />

          <input
            name="password"
            type="password"
            placeholder="Password"
            value={user.password}
            onChange={handleChange}
          />

          <button onClick={handleLogin} disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>

          <p className="switch-text">
            Don't have an account?
            <span onClick={() => navigate("/register")}>
              Register here
            </span>
          </p>

        </div>

      </div>
    </div>
  );
}
