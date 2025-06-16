import React, { useEffect, useState } from "react";
import styles from "../styles/Dashboard.module.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

const UserInformationStudent = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    name: "",
    surname: "",
    username: "",
    facultyName: "",
    studentId: "",
  });

 

  useEffect(() => {
    const fetchStudentInfo = async () => {
      const token = localStorage.getItem("token");
      const storedUsername = localStorage.getItem("username");
      const storedRole = localStorage.getItem("role");

      if (!token || !storedUsername || !storedRole) {
        toast.error("Authentication required. Please log in again.");
        navigate(`/login/student`);
        return;
      }

      try {
        const res = await fetch(`http://localhost:8080/api/student/me`, {
         
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) {
          const errorText = await res.text();
          let errorData = {};
          try {
            errorData = JSON.parse(errorText);
          } catch (e) {
            console.warn(
              "Backend response for student info is not JSON:",
              errorText
            );
          }

          const errorMessageFromBackend =
            errorData.exception?.message || "Failed to load user information.";

          if (errorMessageFromBackend.includes("Your session has timed out")) {
            toast.error("Your session has timed out. Please log in again.");
            localStorage.removeItem("token");
            localStorage.removeItem("username");
            localStorage.removeItem("role");
            navigate("/login/student");
          } else {
            toast.error(
              `Failed to load student info: ${errorMessageFromBackend}`
            );
          }
          setUser({
            name: "",
            surname: "",
            username: "",
            facultyName: "",
            studentId: "",
          });
          return;
        }

        const data = await res.json();
        setUser({
          name: data.name,
          surname: data.surname,
          username: data.username,
          facultyName: data.facultyName || "N/A",
          studentId: data.username, 
        });
      } catch (err) {
        console.error("Fetch student info error:", err);
        toast.error(
          "An unexpected error occurred while loading student information."
        );
      }
    };

    fetchStudentInfo();
  }, [navigate]);

  

  return (
    <div className={styles.container}>
      <div className={styles.sidebar}>
        <div className={styles.menuItem}>
          <a>Lectures</a>
          <div className={styles.submenu}>
            <a href="/student/notes">Note List</a>
            <a href="/student/classes">Classes</a>
          </div>
        </div>
        <div className={styles.menuItem}>
          <a href="/student/user">User Information</a>
        </div>
      </div> 
      <div className={styles.main}>
        <div className={styles.header}>
          <a href="/student" className={styles.homeIcon}>
            <i className="fas fa-home"></i>
          </a>
          User Information
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>Log out
          </a>
        </div>

        <div className={styles.middle}>
          <h2>User Information</h2>
          <div style={{ color: "rgb(77, 77, 113)", fontSize: "17px" }}>
            <p>
              Name, Surname and Department changes are made by your Department
              Secretary or Student Affairs.
            </p>
          </div>

          <div className={styles.userCard}>
            <p>
              <strong>Name:</strong> {user.name}
            </p>
            <p>
              <strong>Surname:</strong> {user.surname}
            </p>
            <p>
              <strong>Faculty:</strong> {user.facultyName}
            </p>
            <p>
              <strong>Student ID:</strong> {user.studentId}
            </p>

            <div style={{ marginTop: "20px" }}>
              <label style={{ marginRight: "10px" }}>
                <strong>Password:</strong> ••••••••
              </label>
              <div>
                <a
                  href="#"
                  onClick={(e) => {
                    e.preventDefault();
                    const currentUsername = localStorage.getItem("username");
                    const currentUserRole = localStorage.getItem("role");

                    if (currentUsername && currentUserRole) {
                      toast.info(
                        "You will be redirected to the password change page."
                      );
                      setTimeout(() => {
                        navigate("/changePassword", {
                          state: {
                            username: currentUsername,
                            role: currentUserRole,
                          },
                        });
                      }, 1500);
                    } else {
                      toast.error(
                        "User information not found for password change. Please log in again."
                      );
                    }
                  }}
                >
                  Change password
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
      {}
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default UserInformationStudent;
