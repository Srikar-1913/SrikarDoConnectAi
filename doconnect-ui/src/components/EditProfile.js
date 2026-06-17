import { useState, useEffect } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/editprofile.css";

export default function EditProfile() {

    const navigate = useNavigate();

    const roleFromStorage = localStorage.getItem("role");

    const [userId, setUserId] = useState(null);

    const [user, setUser] = useState({
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
        role: ""
    });

    useEffect(() => {
        loadUser();
    }, []);

    // ✅ FIXED: Get user by ID (NOT /users/all)
    const loadUser = async () => {
        try {
            const id = localStorage.getItem("userId");

            if (!id) {
                console.log("User ID not found");
                return;
            }

            // ✅ CORRECT API
            const res = await API.get(`/users/${id}`);

            setUserId(id);

            setUser({
                name: res.data.name,
                email: res.data.email,
                password: "",
                confirmPassword: "",
                role: res.data.role
            });

        } catch (err) {
            console.error("LOAD ERROR:", err);
        }
    };

    const handleChange = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!userId) {
            alert("User ID missing");
            return;
        }

        if (user.password !== user.confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        const payload = {
            name: user.name,
            email: user.email,
            role: user.role
        };

        if (user.password && user.password.trim() !== "") {
            payload.password = user.password;
        }

        try {
            // ✅ CORRECT UPDATE API
            const res = await API.put(`/users/${userId}`, payload);

            console.log("UPDATE SUCCESS:", res.data);

            // ✅ UPDATE LOCAL STORAGE
            localStorage.setItem("name", payload.name);
            localStorage.setItem("email", payload.email);

            alert("Profile updated");

            navigate("/profile");

        } catch (err) {
            console.log("UPDATE ERROR:", err.response || err);
            alert("Update failed");
        }
    };

    return (
        <div className="edit-container">

            <h2>Edit Profile</h2>
            <p className="subtitle">Update your profile information</p>

            <div className="edit-card">

                <div className="profile-left">
                    <div className="avatar">
                        {user.name ? user.name.charAt(0).toUpperCase() : "U"}
                    </div>
                </div>

                <form className="profile-form" onSubmit={handleSubmit}>

                    <div className="form-group">
                        <label>Full Name</label>
                        <input
                            name="name"
                            value={user.name}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Email</label>
                        <input
                            name="email"
                            value={user.email}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            name="password"
                            value={user.password}
                            onChange={handleChange}
                            placeholder="Enter new password"
                        />
                    </div>

                    <div className="form-group">
                        <label>Confirm Password</label>
                        <input
                            type="password"
                            name="confirmPassword"
                            value={user.confirmPassword}
                            onChange={handleChange}
                        />
                    </div>

                    {roleFromStorage === "ADMIN" && (
                        <div className="form-group">
                            <label>Role</label>
                            <select
                                name="role"
                                value={user.role}
                                onChange={handleChange}
                            >
                                <option value="USER">USER</option>
                                <option value="ADMIN">ADMIN</option>
                            </select>
                        </div>
                    )}

                    <div className="button-group">

                        <button
                            type="button"
                            className="btn cancel"
                            onClick={() => navigate("/profile")}
                        >
                            Cancel
                        </button>

                        <button type="submit" className="btn submit">
                            Update Profile
                        </button>

                    </div>

                </form>
            </div>
        </div>
    );
}