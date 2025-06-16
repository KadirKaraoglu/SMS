import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styles from "../styles/LoginPage.module.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { setToken, setUserRole } from "../services/authService";

const ChangePassword = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { role } = location.state || {}; 

  // State to hold user-entered username and old password
  const [username, setUsername] = useState("");
  const [oldPassword, setOldPassword] = useState("");

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleChangePassword = async () => {
    setErrorMessage(""); 

    // Validate if all necessary fields are filled
    if (!username || !oldPassword || !newPassword || !confirmPassword) {
      setErrorMessage("Please fill in all fields.");
      toast.error("Please fill in all fields.");
      return;
    }

    // Validate if new passwords match
    if (newPassword !== confirmPassword) {
      setErrorMessage("New passwords do not match.");
      toast.error("New passwords do not match.");
      return;
    }

    try {
      const res = await fetch(
        `http://localhost:8080/api/auth/change-password`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            username,      // Use the username from state
            oldPassword,   // Use the oldPassword from state
            newPassword,
          }),
        }
      );

      const data = await res.json(); 

      if (res.ok) {
        toast.success("Password changed successfully!");

        if (data.token) {
          setToken(data.token);
          if (role) {
            setUserRole(role);
          }
        }

       setTimeout(() => navigate(`/login/${(role || '').toLowerCase()}`), 1500);
      } else {
        toast.error(data?.exception?.message || data?.message || "Failed to change password.");
        console.error("Backend error response:", data); 
      }
    } catch (err) {
      console.error("Error during password change request:", err);
      toast.error("An error occurred while changing password.");
    }
  };

  return (
    <div>
      <div className={styles.upper}>Password Change</div>
      <div className={styles.middle}>
        <div className={styles.userline}>
          <h3>Change Password</h3>

          {/* Username Input Field - Now user editable */}
          <div className={styles.userLine}>
            <input
              type="text"
              placeholder="username"
              value={username} // Bind value to state
              onChange={(e) => setUsername(e.target.value)} // Update state on change
              style={{ width: "100%", paddingRight: "30px" }}
            />
          </div>
          
          {/* Old Password Input Field - Now user editable */}
          <div className={styles.userLine}>
            <input
              type="password" 
              placeholder="old password"
              value={oldPassword} // Bind value to state
              onChange={(e) => setOldPassword(e.target.value)} // Update state on change
              style={{ width: "100%", paddingRight: "30px" }}
            />
          </div>

          {/* New Password Input Field */}
          <div className={styles.userLine}>
            <input
              type={showNewPassword ? "text" : "password"}
              placeholder="New password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              style={{ width: "100%", paddingRight: "30px" }}
            />
            <i
              className={`fas ${showNewPassword ? "fa-eye-slash" : "fa-eye"}`}
              onClick={() => setShowNewPassword((prev) => !prev)}
              style={{
                position: "absolute",
                cursor: "pointer",
                color: "#333",
                right: "35px",
                top: "47%", // Original top position
              }}
            />
          </div>

          {/* Confirm Password Input Field */}
          <div className={styles.userLine}>
            <input
              type={showConfirmPassword ? "text" : "password"}
              placeholder="Confirm password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className={styles.formControl}
            />
            <i
              className={`fas ${
                showConfirmPassword ? "fa-eye-slash" : "fa-eye"
              }`}
              onClick={() => setShowConfirmPassword((prev) => !prev)}
              style={{
                position: "absolute",
                cursor: "pointer",
                color: "#333",
                right: "35px",
                top: "60%", 
              }}
            />
          </div>

          {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
         

          <div > {}
            <div className={styles.modalButtons}>
              <button onClick={() => navigate(-1)}>Cancel</button>
            </div>
            <div className={styles.modalButtons}>
              <button onClick={handleChangePassword}>Submit</button>
            </div> 
          </div>
        </div>
      </div>
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default ChangePassword;