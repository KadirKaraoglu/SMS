import styles from "../styles/AdminDashboard.module.css";
import spring1 from "../assets/spring-term.jpg";
import spring2 from "../assets/spring-term_2.jpg";
const AdminPage = () => {
  return (
    <div className={styles.container}>
      <nav className={styles.sidebar}>
        <div className={styles.studentItem}>
          <a href="">Students</a>
          <div className={styles.studentSubmenu}>
            <a href="/admin/student/CoursesStudent">Courses</a>
            <a href="/admin/student/ManageStudent">Manage</a>
          </div>
        </div>
        <div className={styles.teacherItem}>
          <a href="">Teachers</a>
          <div className={styles.teacherSubmenu}>
            <a href="/admin/teacher/ManageTeacher">Manage</a>
          </div>
        </div>
      </nav>

      <div className={styles.main}>
        <div className={styles.header}>
          Admin Panel
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
              This is the main area for managing Students , Student's courses ,
              Teachers and assigning lectures to teachers.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminPage;
