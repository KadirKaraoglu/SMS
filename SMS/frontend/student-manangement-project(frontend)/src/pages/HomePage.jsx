import React from "react";
import styles from"../styles/LoginPage.module.css";

const HomePage = () => {
  return (
    <div>
      <div className={styles.upper}>Central ID Checking System</div>
      <div className={styles.middle}>
        <a href="/login/student">Student Login</a>
        <a href="/login/academician">Academician Login</a>
        <a href="/login/admin">Administor Login</a>
      </div>
    </div>
  );
};

export default HomePage;
