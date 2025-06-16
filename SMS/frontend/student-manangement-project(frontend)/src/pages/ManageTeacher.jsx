import React, { useEffect, useState } from "react";
import styles from "../styles/ManageTeacher.module.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getToken } from "../services/authService";

const ManageTeacher = () => {
  const [teachers, setTeachers] = useState([]);
  const [newTeacher, setNewTeacher] = useState({
    name: "",
    surname: "",
    tcNo: "", // This maps directly to backend's tcNo
    birthDate: "", // Stores date as YYYY-MM-DD internally
  });

  // --- Helper Function: Process API Responses ---
  const handleApiResponse = async (response) => {
    let data = {};
    const text = await response.text();

    if (!response.ok) {
      try {
        if (text) {
          data = JSON.parse(text);
        }
      } catch (e) {
        console.warn("Server error response is not valid JSON:", text, e);
      }
      throw { exception: { message: data.exception?.message || "An unexpected error occurred." } };
    }

    try {
      if (text) {
        data = JSON.parse(text);
      }
    } catch (e) {
      console.warn("Server success response is not valid JSON:", text, e);
      return {};
    }
    return data;
  };

  // --- Function to Fetch Teacher List ---
  const fetchTeachers = async () => {
    try {
      const token = getToken();
      if (!token) {
        throw { exception: { message: "Authentication token not found. Please log in." } };
      }

      const response = await fetch(`http://localhost:8080/api/admin/list-all-teacher`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const data = await handleApiResponse(response);
      setTeachers(data);
    } catch (err) {
      console.error("Failed to fetch teachers:", err);
      toast.error(err.exception?.message || "Failed to load teacher list.");
    }
  };

  useEffect(() => {
    fetchTeachers();
  }, []);

 
  const handleAddTeacher = async (e) => {
    e.preventDefault();

    try {
      
      if (
        !newTeacher.name ||
        !newTeacher.surname ||
        !newTeacher.tcNo ||
        !newTeacher.birthDate
      ) {
        throw { exception: { message: "Please fill in all fields." } };
      }

      

      const token = getToken();
      if (!token) {
        throw { exception: { message: "Authentication token not found. Please log in." } };
      }

      
      const teacherDataToSend = {
        name: newTeacher.name,
        surname: newTeacher.surname,
        tcNo: newTeacher.tcNo, 
      };

      
      if (newTeacher.birthDate) {
        const dateObj = new Date(newTeacher.birthDate);
        const formattedMonth = String(dateObj.getMonth() + 1).padStart(2, '0');
        const formattedDay = String(dateObj.getDate()).padStart(2, '0');
        const formattedYear = dateObj.getFullYear();
        teacherDataToSend.birthDate = `${formattedMonth}/${formattedDay}/${formattedYear}`;
      } else {
        teacherDataToSend.birthDate = "";
      }

      const response = await fetch(`http://localhost:8080/api/admin/save-teacher`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(teacherDataToSend),
      });

      await handleApiResponse(response);

      toast.success("Teacher added successfully!");
      fetchTeachers();
      setNewTeacher({ name: "", surname: "", tcNo: "", birthDate: "" });
    } catch (err) {
      console.error("Add failed:", err);
      toast.error(err.exception?.message || "Failed to add teacher.");
    }
  };

 const handleDeleteTeacher = async (teacherIdToDelete) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this teacher?"
    );
    if (!confirmDelete) return;

    try {
      const token = getToken();
      if (!token) {
        throw { exception: { message: "Authentication token not found. Please log in." } };
      }

      const response = await fetch(`http://localhost:8080/api/admin/delete-teacher/${teacherIdToDelete}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      await handleApiResponse(response);

      toast.success("Teacher deleted successfully!");
      fetchTeachers();
    } catch (err) {
      console.error("Delete failed:", err);
      toast.error(err.exception?.message || "Failed to delete teacher.");
    }
  };

  return (
    <div className={styles.container}>
      <ToastContainer position="top-right" autoClose={3000} />
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
        </div>
        <div className={styles.teacherItem}>
          <a href="/admin/teacher/ManageTeacher">Manage</a>
        </div>
      </nav>
      <div className={styles.main}>
        <div className={styles.header}>
          <a href="/admin" className={styles.homeIcon}>
            <i className="fas fa-home"></i>
          </a>
          Manage Teacher
          <a href="/" className={styles.logout}>
            <i className="fa-solid fa-right-from-bracket"></i>
            Log out
          </a>
        </div>
        <div className={styles.middle}>
          <h2>Add Teacher</h2>
          <form onSubmit={handleAddTeacher} className={styles.form}>
            <input
              type="text"
              placeholder="Name"
              value={newTeacher.name}
              onChange={(e) =>
                setNewTeacher({ ...newTeacher, name: e.target.value })
              }
              className={styles.input}
            />
            <input
              type="text"
              placeholder="Surname"
              value={newTeacher.surname}
              onChange={(e) =>
                setNewTeacher({ ...newTeacher, surname: e.target.value })
              }
              className={styles.input}
            />
            <input
              type="text"
              placeholder="TC Number" 
              value={newTeacher.tcNo}
              onChange={(e) =>
                setNewTeacher({ ...newTeacher, tcNo: e.target.value })
              }
              className={styles.input}
            />
            <div className={styles.datePickerWrapper}>
              <DatePicker
                selected={
                  newTeacher.birthDate ? new Date(newTeacher.birthDate) : null
                }
                onChange={(date) => {
                  if (date) {
                    const year = date.getFullYear();
                    const month = String(date.getMonth() + 1).padStart(2, '0');
                    const day = String(date.getDate()).padStart(2, '0');
                    setNewTeacher({ ...newTeacher, birthDate: `${year}-${month}-${day}` });
                  } else {
                    setNewTeacher({ ...newTeacher, birthDate: "" });
                  }
                }}
                dateFormat="MM/dd/yyyy"
                placeholderText="MM/dd/yyyy"
                className={styles.input}
              />
            </div>
            <button type="submit" className={styles.Button}>
              Add Teacher
            </button>
          </form>
          <h2>Teachers List</h2>
          <table className="table table-bordered bg-white mt-4">
            <thead className={styles.tableCustom}>
              <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>TC Number</th> {}
                <th>Birth Date</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {teachers.map((teacher) => (
                <tr key={teacher.id}> {}
                  <td>{teacher.name}</td>
                  <td>{teacher.surname}</td>
                  <td>{teacher.tcNo}</td> {}
                  <td>{teacher.birthDate}</td> {}
                  <td>
                    <button
                      className={styles.Button}
                      onClick={() => handleDeleteTeacher(teacher.id)}
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

export default ManageTeacher;