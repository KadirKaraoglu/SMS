import React, { useEffect, useState } from "react";
import styles from "../styles/ManageStudent.module.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getToken } from "../services/authService";

const ManageStudent = () => {
  const [students, setStudents] = useState([]);
  const [faculties, setFaculties] = useState([]);
  const [newStudent, setNewStudent] = useState({
    name: "",
    surname: "",
    tcNo: "",
    facultyId: "",
    birthDate: "",
  });
  const [error, setError] = useState("");

  const fetchData = async () => {
    try {
      const token = getToken();
      if (!token) {
        console.error("Authentication token not found.");
        toast.error("You are not authenticated. Please log in.");
        return;
      }

    
      const studentsRes = await fetch(`http://localhost:8080/api/admin/list-all-student`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!studentsRes.ok) {
        const errorText = await studentsRes.text();
        let errorData = {};
        try {
          errorData = JSON.parse(errorText);
        } catch (e) {
          console.warn("Server error response is not valid JSON:", errorText);
        }
        throw new Error(errorData.exception?.message || "Failed to fetch students.");
      }
      const studentsData = await studentsRes.json();
      setStudents(studentsData);

     
      const facultiesRes = await fetch(`http://localhost:8080/api/admin/list-all-faculty`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!facultiesRes.ok) {
        const errorText = await facultiesRes.text();
        let errorData = {};
        try {
          errorData = JSON.parse(errorText);
        } catch (e) {
          console.warn("Server error response is not valid JSON:", errorText);
        }
        throw new Error(errorData.exception?.message || "Failed to fetch faculties.");
      }
      const facultiesData = await facultiesRes.json();
      setFaculties(facultiesData);
    } catch (err) {
      console.error("Failed to fetch data:", err);
      toast.error(err.message || "Failed to load data.");
    }
  };

  useEffect(() => {
    fetchData(); 
  }, []);

  const handleAddStudent = async (e) => {
    e.preventDefault();

    
    if (
      !newStudent.name ||
      !newStudent.surname ||
      !newStudent.tcNo ||
      !newStudent.facultyId ||
      !newStudent.birthDate
    ) {
      toast.error("Please fill in all fields.");
      return;
    }

    
    
    

    
    const studentDataToSend = { ...newStudent };
    if (studentDataToSend.birthDate) {
      const dateObj = new Date(studentDataToSend.birthDate); 
      
      const formattedMonth = String(dateObj.getMonth() + 1).padStart(2, '0');
      const formattedDay = String(dateObj.getDate()).padStart(2, '0');
      const formattedYear = dateObj.getFullYear();
      studentDataToSend.birthDate = `${formattedMonth}/${formattedDay}/${formattedYear}`;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/admin/save-student`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${getToken()}`,
        },
        body: JSON.stringify(studentDataToSend), 
      });

      let data = {};
      const text = await response.text();
      try {
        data = JSON.parse(text);
      } catch (e) {
        if (!response.ok && text.trim() !== "") {
          console.warn("Server response for add student is not valid JSON:", text);
        }
      }

      if (response.ok) {
        toast.success("Student added successfully!");
        fetchData(); 
        setNewStudent({
          name: "",
          surname: "",
          tcNo: "",
          facultyId: "",
          birthDate: "", 
        });
      } else {
        toast.error(data.exception?.message || "Failed to add student!");
      }
    } catch (err) {
      toast.error("Add student error. Please try again.");
      console.error("Add failed:", err);
    }
  };

  const handleDeleteStudent = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this student?"
    );
    if (!confirmDelete) return;

    try {
      const response = await fetch(`http://localhost:8080/api/admin/delete-student/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      });

      let data = {};
      const text = await response.text();
      try {
        data = JSON.parse(text);
      } catch (e) {
        if (!response.ok && text.trim() !== "") {
          console.warn("Server response for delete student is not valid JSON:", text);
        }
      }

      if (response.ok) {
        toast.success("Student deleted successfully!");
        fetchData(); 
      } else {
        toast.error(data.exception?.message || "Failed to delete student!");
      }
    } catch (err) {
      toast.error("Delete student error. Please try again.");
      console.error("Delete failed:", err);
    }
  };

  return (
    <div className={styles.container}>
      <ToastContainer position="top-right" autoClose={3000} />
      <nav className={styles.sidebar}>
        <div className={styles.studentItem}>
          <a href="">Students</a>
        </div>
        <div className={styles.studentItem}>
          <a href="/admin/student/CoursesStudent">Courses</a>
        </div>
        <div className={styles.studentItem}>
          <a href="">Manage</a>
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
          Manage Students
          <a href="/" className={styles.logout}>
            <i className="fa-solid fa-right-from-bracket"></i>
            Log out
          </a>
        </div>
        <div className={styles.middle}>
          <h2>Add Student</h2>
          <form onSubmit={handleAddStudent} className={styles.form}>
            <input
              type="text"
              placeholder="Name"
              value={newStudent.name}
              onChange={(e) =>
                setNewStudent({ ...newStudent, name: e.target.value })
              }
              className={styles.input}
            />
            <input
              type="text"
              placeholder="Surname"
              value={newStudent.surname}
              onChange={(e) =>
                setNewStudent({ ...newStudent, surname: e.target.value })
              }
              className={styles.input}
            />
            <input
              type="text"
              placeholder="ID"
              value={newStudent.tcNo}
              onChange={(e) =>
                setNewStudent({ ...newStudent, tcNo: e.target.value })
              }
              className={styles.input}
            />
            <select
              value={newStudent.facultyId}
              onChange={(e) =>
                setNewStudent({ ...newStudent, facultyId: e.target.value })
              }
              className={styles.input}
            >
              <option value="" disabled hidden>
                Select Faculty
              </option>
              {faculties.map((faculty) => (
                <option key={faculty.facultyId} value={faculty.facultyId}>
                  {faculty.facultyname}
                </option>
              ))}
            </select>
            <div className={styles.datePickerWrapper}>
              <DatePicker
                selected={
                  newStudent.birthDate ? new Date(newStudent.birthDate) : null
                }
                onChange={(date) => {
                  if (date) {
                    
                    const year = date.getFullYear();
                    const month = String(date.getMonth() + 1).padStart(2, '0');
                    const day = String(date.getDate()).padStart(2, '0');
                    setNewStudent({ ...newStudent, birthDate: `${year}-${month}-${day}` });
                  } else {
                    setNewStudent({ ...newStudent, birthDate: "" });
                  }
                }}
                dateFormat="MM/dd/yyyy" 
                placeholderText="MM/dd/yyyy" 
                className={styles.input}
              />
            </div>

            <button type="submit" className={styles.Button}>
              Add Student
            </button>
          </form>
          {error && <p className={styles.error}>{error}</p>}
          <h2>Student List</h2>
          <table className="table table-bordered bg-white mt-4">
            <thead className={styles.tableCustom}>
              <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>ID</th>
                <th>Faculty</th>
                <th>Birth Date</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {students.map((student) => (
                <tr key={student.tcNo}>
                  <td>{student.name}</td>
                  <td>{student.surname}</td>
                  <td>{student.tcNo}</td>
                  <td>{student.facultyName}</td>
                  <td>{student.birthDate}</td>
                  <td>
                    <button
                      className={styles.Button}
                      onClick={() => handleDeleteStudent(student.id)}
                    >
                      Remove
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ManageStudent;