import React from "react";
import styles from "../styles/Dashboard.module.css";
import spring1 from "../assets/spring-term.jpg";
import spring2 from "../assets/spring-term_2.jpg";

const StudentDashboard = () => {
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
        <div className={styles.header}>2024-2025 Spring Term
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>Log out
          </a>
        </div>
        <div className={styles.middle}>
          <div className={styles.overview}>
            <h2>Welcome to the Spring Term!</h2>
            <div className={styles.imageRow}>
              <img src={spring1} alt="Spring Term 1" />
              <img src={spring2} alt="Spring Term 2" />
            </div>
            <p>
              This is the main area for managing and reviewing lecture materials
              for the Spring 2024–2025 term. Stay updated with notes, classes,
              and more!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;
