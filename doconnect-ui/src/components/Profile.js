import "../styles/profile.css";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import API from "../services/api";

// Profile page component
export default function Profile() {

    // State to store number of questions
    const [questionCount, setQuestionCount] = useState(0);

    // Random stats (temporary display)
    const randomStats = {
        questions: Math.floor(Math.random() * 20) + 1,
        answers: Math.floor(Math.random() * 15) + 1,
        messages: Math.floor(Math.random() * 10) + 1,
        likes: Math.floor(Math.random() * 100) + 10
    };

    // Navigation hook
    const navigate = useNavigate();

    // State to store user details
    const [user, setUser] = useState({
        name: "User",
        email: "user@example.com",
        role: "USER"
    });

    // Load user data and question count on page load
    useEffect(() => {
        loadUser();
        loadQuestionCount();
    }, []);

    // Fetch user data from backend
    const loadUser = () => {
        const name = localStorage.getItem("name");
        const email = localStorage.getItem("email");
        const role = localStorage.getItem("role");

        setUser({
            name: name || "User",
            email: email || "user@example.com",
            role: role || "USER"
        });
    };

    // Generate dummy "About Me" details
    const generateAbout = (user) => {

        const locations = ["Hyderabad", "Bangalore", "Chennai", "Mumbai", "Delhi"];
        const educations = ["B.Tech", "M.Tech", "B.Sc", "MBA", "Diploma"];
        const interests = ["Programming", "AI", "Web Development", "Data Science", "Gaming"];

        // Use email length to randomize values
        const base = user.email?.length || 5;

        return {
            location: locations[base % locations.length],
            education: educations[(base * 2) % educations.length],
            interest: interests[(base * 3) % interests.length]
        };
    };

    // Generate about section
    const about = generateAbout(user);

    // Get number of questions posted by current user
    const loadQuestionCount = async () => {
        try {
            const email = localStorage.getItem("email");

            const res = await API.get("/questions");

            // Filter questions by logged-in user
            const myQuestions = res.data.filter(
                q => q.userEmail.toLowerCase() === email.toLowerCase()
            );

            // Set total question count
            setQuestionCount(myQuestions.length);

        } catch (err) {
            console.log("QUESTION LOAD ERROR:", err);
        }
    };

    return (
        <div className="profile-page">

            {/* Header section */}
            <div className="profile-header">
                <h3>My Profile</h3>
                <p>Manage your profile and track your activity</p>
            </div>

            {/* Profile card */}
            <div className="profile-card">

                {/* Avatar section */}
                <div className="profile-left">
                    <div className="profile-avatar">
                        {user.name?.charAt(0) || "U"}
                    </div>
                </div>

                {/* User info */}
                <div className="profile-info">
                    <h4>
                        {user.name}
                        <span className="role-badge">{user.role}</span>
                    </h4>

                    <p>{user.email}</p>

                    <p className="bio">
                        Passionate about learning and sharing knowledge.
                    </p>
                </div>

                {/* Navigate to edit profile */}
                <button
                    className="edit-btn"
                    onClick={() => navigate("/edit-profile")}
                >
                    Edit Profile
                </button>

            </div>

            {/* Stats section */}
            <div className="stats-grid">

                <div className="stat">
                    <h3>{randomStats.questions}</h3>
                    <p>Questions</p>
                </div>

                <div className="stat">
                    <h3>8</h3>
                    <p>Answers</p>
                </div>

                <div className="stat">
                    <h3>5</h3>
                    <p>Messages</p>
                </div>

                <div className="stat">
                    <h3>20</h3>
                    <p>Likes</p>
                </div>

            </div>

            {/* Bottom content */}
            <div className="profile-content">

                {/* Activity section */}
                <div className="activity">
                    <h5>Recent Activity</h5>

                    <ul>
                        <li>Asked a question</li>
                        <li>Answered a question</li>
                        <li>Received a message</li>
                    </ul>
                </div>

                {/* About Me section */}
                <div className="about">
                    <h5>About Me</h5>

                    <p>Location: {about.location}</p>
                    <p>Education: {about.education}</p>
                    <p>Interests: {about.interest}</p>

                </div>

            </div>

        </div>
    );
}