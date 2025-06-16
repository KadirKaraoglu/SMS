import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "../styles/LoginPage.module.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Login = () => {
  const { role } = useParams();
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    if (username.trim() === "" || password.trim() === "") {
      toast.error("Please enter both username and password!");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/auth/login-${role}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      let data = {};
      const text = await response.text();
      try {
        data = JSON.parse(text);
      } catch (e) {
        console.warn("Server response is not valid JSON:", text);
      }

      if (response.ok) {
        toast.success(`Logged in as ${role}!`);
        localStorage.setItem("token", data.token);
        localStorage.setItem("username", data.username);
        localStorage.setItem("role", data.role);

        if (role === "student") navigate("/student");
        else if (role === "academician") navigate("/academician");
        else if (role === "admin") navigate("/admin");
      } else {
       if (data?.exception?.message === "Please Change password first login") {
          toast.info("First login: Please change your password. You will be redirected to the password change page.");
          setTimeout(() => {
            navigate("/changePassword", {
              state: { username, role, oldPassword: password },
            });
          }, 3000);
        } else {
          toast.error(data.exception?.message || "Login failed!");
        }
      }
    } catch (error) {
      toast.error("Login error. Please try again.");
      console.error("Login error:", error);
    }
  };

  const Header = () => {
    switch (role) {
      case "student":
        return "Student Login System";
      case "academician":
        return "Academician Login System";
      case "admin":
        return "Administrator Login System";
      default:
        return "Login";
    }
  };

  return (
    <div>
      <div className={styles.upper}>{Header()}</div>
      <div className={styles.middle}>
        <h3>Central ID Checking Service</h3>

        <div className={styles.userLine}>
          <input
            type="text"
            className={styles.formControl}
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <span className={styles.iconWrapper}>
            <i className="fas fa-user"></i>
          </span>
        </div>

        <div className={styles.passwordLine}>
          <input
            type="password"
            className={styles.formControl}
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <span className={styles.iconWrapper}>
            <i className="fas fa-lock"></i>
          </span>
        </div>

        <button onClick={handleLogin}>Login</button>
      </div>

      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default Login;
