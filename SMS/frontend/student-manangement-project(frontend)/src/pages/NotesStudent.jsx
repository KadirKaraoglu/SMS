import React, { useState, useEffect } from "react";
import styles from "../styles/NotesStudent.module.css";
import { getToken } from "../services/authService";

const NotesStudent = () => {
  const [grades, setGrades] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchGrades = async () => {
      setLoading(true);
      setError(null);
      const token = getToken();
      
      if (!token) {
        setError("You are not logged in. Please log in to view your notes.");
        setLoading(false);
        return;
      }

      try {
        // Ensure this URL is your actual backend endpoint for fetching student grades
        // Example: `http://localhost:8080/api/student/my-grades` or `http://localhost:8080/api/student/{studentId}/grades`
        const res = await fetch(
          `http://localhost:8080/api/grade/list-all-grade-of-student`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!res.ok) {
          const errorText = await res.text();
          let errorMessage = "Failed to fetch grades.";
          try {
            const errorData = JSON.parse(errorText);
            errorMessage = errorData?.exception?.message || errorMessage;
          } catch (e) {
            errorMessage = errorText || errorMessage;
          }
          throw new Error(errorMessage);
        }

        const data = await res.json();
        setGrades(data);
      } catch (err) {
        setError(err.message);
        console.error("Error loading grades:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchGrades();
  }, []);

  const calculateLetter = (midterm, final) => {
    const parsedMidterm =
      midterm === null || midterm === undefined ? 0 : Number(midterm);
    const parsedFinal =
      final === null || final === undefined ? 0 : Number(final);

    
    if (
      (midterm === null || midterm === undefined) &&
      (final === null || final === undefined)
    ) {
      return "N/A";
    }

    const avg = parsedMidterm * 0.4 + parsedFinal * 0.6;

    if (avg >= 90) return "AA";
    else if (avg >= 85) return "BA";
    else if (avg >= 80) return "BB";
    else if (avg >= 75) return "CB";
    else if (avg >= 70) return "CC";
    else if (avg >= 65) return "DC";
    else if (avg >= 60) return "DD";
    else if (avg >= 50) return "FD";
    else return "FF";
  };

  if (loading) {
    return <div className={styles.loading}>Loading your exam notes...</div>;
  }

  if (error) {
    return <div className={styles.error}>An error occurred: {error}</div>;
  }

  return (
    <div className={styles.container}>
      <nav className={styles.sidebar}>
        <div className={styles.menuItem}>
          <a href="/student/lectures">Lectures</a>
        </div>
        <div className={styles.menuItem}>
          <a href="#">Note List</a>
        </div>
        <div className={styles.menuItem}>
          <a href="/student/classes">Classes</a>
        </div>
        <div className={styles.menuItem}>
          <a href="/student/user">User Information</a>
        </div>
      </nav>

      <div className={styles.main}>
        <div className={styles.header}>
          <a href="/student" className={styles.homeIcon}>
            <i className="fas fa-home"></i>
          </a>
          Exam Notes
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>Log out
          </a>
        </div>

        <div className={styles.middle}>
          <h2>Term Courses</h2>
          <div className="table-responsive">
            {grades.length > 0 ? (
              <table className="table table-bordered table-striped bg-white">
                <thead className={styles.tableCustom}>
                  <tr>
                    <th>Lecture Code</th>
                    <th>Lecture</th>
                    <th>Teacher</th>
                    <th>Midterm Grade</th>
                    <th>Final Grade</th>
                    <th>Letter Grade</th>
                  </tr>
                </thead>
                <tbody>
                  {grades.map((grade) => (
                    <tr key={grade.gradeId}>
                      <td>{grade.courseCode || "N/A"}</td>
                      <td>{grade.courseName}</td>
                      <td>{grade.courseTeacherName || "N/A"}</td>
                      <td>{grade.midterm ?? "N/A"}</td>
                      <td>{grade.finalGrade ?? "N/A"}</td>
                      <td>
                        {calculateLetter(grade.midterm, grade.finalGrade)}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            ) : (
              <p className={styles.noDataMessage}>
                You have no selected courses for this Term or no grades are
                available yet.
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default NotesStudent;
