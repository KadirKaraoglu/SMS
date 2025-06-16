import React from "react";
import styles from "../styles/AcademicianDashboard.module.css";
import spring1 from "../assets/spring-term.jpg";
import spring2 from "../assets/spring-term_2.jpg";

const AcademicianDashboard = () => {
  return (
    <div className={styles.container}>
      <div className={styles.sidebar}>
        <div className={styles.menuItem}>
          <a>Students</a>
          <div className={styles.submenu}>
            <a href="/academician/notes">Note Entry</a>
          </div>
        </div>
        <div className={styles.menuItem}>
          <a href="/academician/user">User Information</a>
        </div>
      </div>

      <div className={styles.main}>
        <div className={styles.header}>
          2024-2025 Spring Term
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>
            Log out
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
              This is the main area for managing midterm and final notes for the
              Spring 2024–2025 term.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AcademicianDashboard;
