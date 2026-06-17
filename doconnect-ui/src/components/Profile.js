import "../styles/profile.css";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import API from "../services/api";

export default function Profile() {

    const [questionCount, setQuestionCount] = useState(0);

    const randomStats = {
        questions: Math.floor(Math.random() * 20) + 1,
        answers: Math.floor(Math.random() * 15) + 1,
        messages: Math.floor(Math.random() * 10) + 1,
        likes: Math.floor(Math.random() * 100) + 10
    };

    const navigate = useNavigate();

    // ✅ NEW STATE
    const [user, setUser] = useState({
        name: "User",
        email: "user@example.com",
        role: "USER"
    });

    useEffect(() => {
        loadUser();
        loadQuestionCount();
    }, []);

    const loadUser = async () => {
        try {
            const email = localStorage.getItem("email");

            if (!email) return;

            const res = await API.get("/users/all");

            const currentUser = res.data.find(
                u => u.email.toLowerCase() === email.toLowerCase()
            );

            if (!currentUser) return;

            // ✅ update state (this fixes name issue)
            setUser(currentUser);

        } catch (err) {
            console.log("PROFILE LOAD ERROR:", err);
        }
    };

    const generateAbout = (user) => {

        const locations = ["Hyderabad", "Bangalore", "Chennai", "Mumbai", "Delhi"];
        const educations = ["B.Tech", "M.Tech", "B.Sc", "MBA", "Diploma"];
        const interests = ["Programming", "AI", "Web Development", "Data Science", "Gaming"];

        const base = user.email?.length || 5;

        return {
            location: locations[base % locations.length],
            education: educations[(base * 2) % educations.length],
            interest: interests[(base * 3) % interests.length]
        };
    };

    const about = generateAbout(user);
    const loadQuestionCount = async () => {
        try {
            const email = localStorage.getItem("email");

            const res = await API.get("/questions");

            // ✅ filter questions by logged-in user
            const myQuestions = res.data.filter(
                q => q.userEmail.toLowerCase() === email.toLowerCase()
            );

            setQuestionCount(myQuestions.length);

        } catch (err) {
            console.log("QUESTION LOAD ERROR:", err);
        }
    };


    return (
        <div className="profile-page">

            <div className="profile-header">
                <h3>My Profile</h3>
                <p>Manage your profile and track your activity</p>
            </div>

            <div className="profile-card">

                <div className="profile-left">
                    <div className="profile-avatar">
                        {user.name?.charAt(0) || "U"}
                    </div>
                </div>

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

                <button
                    className="edit-btn"
                    onClick={() => navigate("/edit-profile")}
                >
                    Edit Profile
                </button>

            </div>

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

            <div className="profile-content">

                <div className="activity">
                    <h5>Recent Activity</h5>

                    <ul>
                        <li>Asked a question</li>
                        <li>Answered a question</li>
                        <li>Received a message</li>
                    </ul>
                </div>

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
