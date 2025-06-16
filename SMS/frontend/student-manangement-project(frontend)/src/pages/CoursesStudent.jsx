import React, { useState, useEffect } from "react";
import styles from "../styles/CoursesStudent.module.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getToken } from "../services/authService";

const CoursesStudent = () => {
  
  const [course, setCourse] = useState({
    courseCode: "",
    courseName: "",
    teacherId: "", 
    credit: "",
    akts: "",
    quota: "",
  });

 
  const [courses, setCourses] = useState([]);
 
  const [teachers, setTeachers] = useState([]);

  useEffect(() => {
    const token = getToken();

  
    if (!token) {
      toast.error("You are not authorized. Please log in.");
      return;
    }

    const fetchCourses = () => {
      fetch(`http://localhost:8080/api/course/list-all-course`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
          }
          return res.json();
        })
        .then((data) => setCourses(data))
        .catch((err) => {
          console.error("Error fetching courses:", err);
          toast.error("Failed to load courses.");
        });
    };

    const fetchTeachers = () => {
      fetch(`http://localhost:8080/api/teacher/list-all-teacher`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
          }
          return res.json();
        })
        .then((data) => setTeachers(data))
        .catch((err) => {
          console.error("Error fetching teachers:", err);
          toast.error("Failed to load teachers.");
        });
    };

    fetchCourses();
    fetchTeachers();
  }, []); 


  const handleCourseSubmit = (e) => {
    e.preventDefault();
    const token = getToken();

    if (!token) {
      toast.error("Authentication required to add a course.");
      return;
    }

    fetch(`http://localhost:8080/api/admin/save-course`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
 
      body: JSON.stringify({
        courseCode: course.courseCode,
        courseName: course.courseName,
        teacherId: course.teacherId,
        credit: Number(course.credit),
        akts: Number(course.akts),
        quota: Number(course.quota),
      }),
    })
      .then((res) => {
        if (!res.ok) {
          return res.json().then((err) => {
            throw new Error(err.message || "Failed to add course");
          });
        }

     
        return res.text().then((text) => {
          return text ? JSON.parse(text) : {};
        });
      })

      .then(() => {
        toast.success("Course added successfully!");
        
        setCourse({
          courseCode: "",
          courseName: "",
          teacherId: "",
          credit: "",
          akts: "",
          quota: "",
        });
        
        fetch(`http://localhost:8080/api/course/list-all-course`, {
          headers: { Authorization: `Bearer ${token}` },
        })
          .then((res) => res.json())
          .then((data) => setCourses(data));
      })
      .catch((err) => {
        console.error("Error adding course:", err);
        toast.error(`Failed to add course: ${data.exception?.message || "Unknown error"}`);
      });
  };

  
  const handleDeleteCourse = (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this course?"
    );
    if (confirmDelete) {
      const token = getToken();

      if (!token) {
        toast.error("Authentication required to delete a course.");
        return;
      }

     
      fetch(`http://localhost:8080/api/admin/delete-course/${id}`, {
       
        headers: {
          Authorization: `Bearer ${token}`,
        },
        method: "DELETE",
      })
        .then((res) => {
          if (res.ok) {
            toast.success("Course deleted successfully!");
            
            return fetch(`http://localhost:8080/api/course/list-all-course`, {
              headers: { Authorization: `Bearer ${token}` },
            });
          } else {
       
            return res.json().then((err) => {
              throw new Error(err.message || "Error deleting course");
            });
          }
        })
        .then((res) => res.json())
        .then((data) => setCourses(data))
        .catch((err) => {
          console.error("Error deleting course:", err);
          toast.error(
            `Failed to delete course: ${err.message || "Unknown error"}`
          );
        });
    }
  };


  const getTeacherNameById = (teacherId) => {
    const teacher = teachers.find((t) => t.id === teacherId);
    return teacher ? `${teacher.name} ${teacher.surname}` : "Unknown";
  };

  return (
    <div className={styles.container}>
      <nav className={styles.sidebar}>
        <div className={styles.studentItem}>
          <a href="">Students</a>
        </div>
        <div className={styles.studentItem}>
          <a href="">Courses</a>
        </div>
        <div className={styles.studentItem}>
          <a href="/admin/student/ManageStudent">Manage</a>
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
          <a href="/admin" className={styles.homeIcon}>
            <i className="fas fa-home"></i>
          </a>
          Manage Courses
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>
            Log out
          </a>
        </div>

        <div className={styles.middle}>
          <h2>Add New Course</h2>
          <form onSubmit={handleCourseSubmit} className={styles.form}>
            <input
              type="text"
              placeholder="Course Code"
              value={course.courseCode}
              onChange={(e) =>
                setCourse({ ...course, courseCode: e.target.value })
              }
              className={styles.input}
            />
            <input
              type="text"
              placeholder="Course Name"
              value={course.courseName}
              onChange={(e) =>
                setCourse({ ...course, courseName: e.target.value })
              }
              className={styles.input}
            />

            <select
              value={course.teacherId}
              onChange={(e) =>
                setCourse({ ...course, teacherId: e.target.value })
              }
              className={styles.input}
            >
              <option value="" disabled hidden>
                Select Teacher
              </option>
              {teachers.map((teach) => (
                <option key={teach.id} value={teach.id}>
                  {teach.name} {teach.surname}
                </option>
              ))}
            </select>

            <input
              type="number"
              placeholder="Credit"
              value={course.credit}
              onChange={(e) => setCourse({ ...course, credit: e.target.value })}
              className={styles.input}
            />
            <input
              type="number"
              placeholder="AKTS"
              value={course.akts}
              onChange={(e) => setCourse({ ...course, akts: e.target.value })}
              className={styles.input}
            />
            <input
              type="number"
              placeholder="Quota"
              value={course.quota}
              onChange={(e) => setCourse({ ...course, quota: e.target.value })}
              className={styles.input}
            />
            <button type="submit" className={styles.Button}>
              Add Course
            </button>
          </form>

          <h2>Current Courses</h2>
          <table className="table table-bordered table-striped bg-white mt-4">
            <thead className={styles.tableCustom}>
              <tr>
                <th>Course Code</th>
                <th>Course Name</th>
                <th>Teacher</th>
                <th>Credit</th>
                <th>AKTS</th>
                <th>Quota</th>
                <th>Actions</th> {}
              </tr>
            </thead>
            <tbody>
              {courses.map((c) => (
                <tr key={c.courseId}>
                  <td>{c.courseCode || "N/A"}</td>
                  <td>{c.courseName}</td>
                  <td>{c.teacherName || "N/A"}</td>{" "}
                  {}
                  <td>{c.credit}</td>
                  <td>{c.akts}</td>
                  <td>{c.quota}</td>
                  <td>
                    <button
                      onClick={() => handleDeleteCourse(c.courseId)}
                      className={styles.Button}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default CoursesStudent;
