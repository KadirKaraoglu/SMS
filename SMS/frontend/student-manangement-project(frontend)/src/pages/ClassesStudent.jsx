/*Students Choosing their lectures for Spring term */

import React, { useEffect, useState } from "react";
import styles from "../styles/Classes.module.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getToken } from "../services/authService"; 

const Classes = () => {
  const [availableCourses, setAvailableCourses] = useState([]);
  const [selectedCourses, setSelectedCourses] = useState([]);
  const [totalAKTS, setTotalAKTS] = useState(0);
  const [totalCredit, setTotalCredit] = useState(0);
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(true); 
  const [error, setError] = useState(null); 

  
  useEffect(() => {
    const token = getToken(); 
    if (!token) {
      setError("Authentication token not found. Please log in.");
      setLoading(false);
      return;
    }

    fetch(`http://localhost:8080/api/course/list-all-course`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          
          if (res.status === 401) {
            throw new Error("Unauthorized access. Please log in again.");
          }
          throw new Error(`Network response was not ok: ${res.statusText}`);
        }
        return res.json();
      })
      .then((data) => {
        
        const adaptedCourses = data.map((course) => ({
          code: course.courseId, 
          name: course.courseName, 
          teacher: course.teacherName || "N/A",
          credit: course.credit,
          akts: course.akts,
          quota: course.quota,
          enrolled: course.sumOfCurrentStudent, 
        }));
        setAvailableCourses(adaptedCourses);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching courses:", err);
        setError(err.message);
        setLoading(false); 
        toast.error(`Failed to load courses: ${err.message}`);
      });
  }, []); 

 
  const handleSelectCourse = (course) => {
    if (submitted) return; 

    setSelectedCourses((prev) => [...prev, course]);
   
    setTotalAKTS((prev) => prev + Number(course.akts));
    setTotalCredit((prev) => prev + Number(course.credit));

    
    const updatedCourses = availableCourses.map((c) =>
      c.code === course.code ? { ...c, enrolled: c.enrolled + 1 } : c
    );
    setAvailableCourses(updatedCourses);
    toast.success(`${course.name} added to your selections.`);
  };

 
  const handleRemoveCourse = (courseCode) => {
    if (submitted) return; 

    const removedCourse = selectedCourses.find(
      (course) => course.code === courseCode
    );
    if (!removedCourse) return;

    const updatedSelected = selectedCourses.filter(
      (course) => course.code !== courseCode
    );
    setSelectedCourses(updatedSelected);

    
    setTotalAKTS((prev) => Math.max(0, prev - Number(removedCourse.akts)));
    setTotalCredit((prev) => Math.max(0, prev - Number(removedCourse.credit)));

   
    const updatedCourses = availableCourses.map((c) =>
      c.code === removedCourse.code ? { ...c, enrolled: c.enrolled - 1 } : c
    );
    setAvailableCourses(updatedCourses);
    toast.info(`${removedCourse.name} removed from your selections.`);
  };

  
  const handleSubmit = async () => {
    
    if (submitted) {
      toast.info("Your courses have already been submitted.");
      return;
    }
    
    if (selectedCourses.length === 0) {
      toast.warn("You must select at least one course before submitting.");
      return;
    }

   
    const token = getToken();
    if (!token) {
      toast.error("Authentication token not found. Please log in.");
      return;
    }

    try {
      
      const courseIdsToSubmit = selectedCourses.map((course) => {
       
        return Number(course.code);
      });

     
      const payload = {
        courseIds: courseIdsToSubmit, 
      };

      
      const res = await fetch(
        `http://localhost:8080/api/course/add-student-course`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        }
      );

      
      if (!res.ok) {
        let errorData;
        try {
          
          errorData = await res.json();
        } catch (jsonError) {
          
          throw new Error(`Network response was not ok: ${res.statusText}`);
        }

        
        const errorMessage =
          errorData?.exception?.message || "Failed to submit courses.";
        throw new Error(errorMessage);
      }

      
      const result = await res.json();
      setSubmitted(true); 
      toast.success(result.message || "Courses submitted successfully!"); 

      
      setSelectedCourses([]);
      
    } catch (err) {
     
      console.error("Error submitting courses:", err);
      toast.error(
        `An error occurred while submitting your courses: ${err.message}`
      );
    }
  };

  if (loading) {
    return <div className={styles.loading}>Loading courses...</div>;
  }

  if (error) {
    return <div className={styles.error}>Error: {error}</div>;
  }

  return (
    <div className={styles.body}>
      <nav className={styles.sidebar}>
        <div className={styles.menuItem}>
          <a href="/student/classes">Lectures</a> {}
        </div>
        <div className={styles.menuItem}>
          <a href="/student/notes">Note List</a>
        </div>
        <div className={styles.menuItem}>
          <a href="/student/classes">Classes</a>{" "}
          {}
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
          Course Selection
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>Log out
          </a>
        </div>

        <div className={`${styles.middle} p-4`}>
          <h2>Available Courses</h2>
          <div className="table-responsive">
            <table
              id="available-courses"
              className="table table-bordered table-striped bg-white"
            >
              <thead className={styles.tableCustom}>
                <tr>
                  <th>Lecture Code</th>
                  <th>Lecture</th>
                  <th>Teacher</th>
                  <th>Credit</th>
                  <th>AKTS</th>
                  <th>Quota</th>
                  <th>Enrolled</th>
                  <th>Select</th>
                </tr>
              </thead>
              <tbody>
                {availableCourses.length > 0 ? (
                  availableCourses.map((course) => (
                    <tr key={course.code}>
                      <td>{course.code}</td>
                      <td>{course.name}</td>
                      <td>{course.teacher}</td>
                      <td>{course.credit}</td>
                      <td>{course.akts}</td>
                      <td>{course.quota}</td>
                      <td>{course.enrolled}</td>
                      <td>
                        <button
                          className={styles.Button}
                          onClick={() => handleSelectCourse(course)}
                          disabled={
                            submitted || course.enrolled >= course.quota
                          } 
                        >
                          {course.enrolled >= course.quota ? "Full" : "Select"}
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="8">No available courses to display.</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

          <h2>Selected Courses</h2>
          <div className="table-responsive">
            <table
              id="selected-courses"
              className="table table-bordered table-striped bg-white"
            >
              <thead className={styles.tableCustom}>
                <tr>
                  <th>Lecture Code</th>
                  <th>Lecture</th>
                  <th>Teacher</th>
                  <th>Credit</th>
                  <th>AKTS</th>
                  <th>Enrolled</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {selectedCourses.length > 0 ? (
                  selectedCourses.map((course) => (
                    <tr key={course.code}>
                      <td>{course.code}</td>
                      <td>{course.name}</td>
                      <td>{course.teacher}</td>
                      <td>{course.credit}</td>
                      <td>{course.akts}</td>
                      <td>{course.enrolled}</td>
                      <td>
                        <button
                          className={styles.Button}
                          onClick={() => handleRemoveCourse(course.code)}
                          disabled={submitted} 
                        >
                          Remove
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7">No courses selected yet.</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

          <div className={styles.totals}>
            <div>
              Total AKTS: <span>{totalAKTS}</span>
              <br />
              Total Credit: <span>{totalCredit}</span>
            </div>
            <div className="mt-4">
              {!submitted ? (
                <button className={styles.submit} onClick={handleSubmit}>
                  Submit Courses
                </button>
              ) : (
                <p>Your courses have been submitted and cannot be changed.</p>
              )}
            </div>
          </div>
        </div>
      </div>
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default Classes;
