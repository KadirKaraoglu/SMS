import React, { useEffect, useState } from "react";
import styles from "../styles/Notes.module.css";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getToken } from "../services/authService";

const NoteEntry = () => {
  const [lectures, setLectures] = useState([]);
  const [teacher, setTeacher] = useState(null);
  const [savingLectureId, setSavingLectureId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserAndLectures = async () => {
      setLoading(true);
      setError(null);
      const token = getToken();
      if (!token) {
        toast.error("You must be logged in to access this page.");
        setLoading(false);
        return;
      }

      try {
        const userRes = await fetch(`http://localhost:8080/api/teacher/me`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!userRes.ok) {
          const errorText = await userRes.text();
          let errorData = {};
          try {
            errorData = JSON.parse(errorText);
          } catch (e) {
            console.warn("Backend user info response is not JSON:", errorText);
          }
          const errorMessage =
            errorData?.exception?.message || "Failed to load user information.";
          throw new Error(errorMessage);
        }

        const userData = await userRes.json();
        setTeacher({
          id: userData.id,
          name: userData.name,
          surname: userData.surname,
          username: userData.username,
        });

        const lectureRes = await fetch(
          `http://localhost:8080/api/teacher/list-course-of-teacher`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!lectureRes.ok) {
          const errorText = await lectureRes.text();
          let errorData = {};
          try {
            errorData = JSON.parse(errorText);
          } catch (e) {
            console.warn(
              "Backend lecture info response is not JSON:",
              errorText
            );
          }
          const errorMessage =
            errorData?.exception?.message || "Failed to load lectures.";
          throw new Error(errorMessage);
        }

        const lecturesData = await lectureRes.json();

        const adaptedLectures = lecturesData.map((course) => ({
          id: course.courseId,
          code: course.courseId,
          name: course.courseName,
          teacher: course.teacherName,
          credit: course.credit,
          akts: course.akts,
          quota: course.quota,
          enrolled: course.sumOfCurrentStudent,
          students: course.dtoStudents
            ? course.dtoStudents.map((student) => ({
                id: student.id,
                name: student.name,
                surname: student.surname,
                midterm: null, 
                finalGrade: null,
              }))
            : [],
        }));

        setLectures(adaptedLectures);
      } catch (err) {
        setError(err.message);
        toast.error(`Error fetching data: ${err.message}`);
        console.error("Fetch data error:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchUserAndLectures();
  }, []);

  const handleGradeChange = (lectureId, studentId, field, value) => {
    const gradeValue = value;

    setLectures((prevLectures) => {
      return prevLectures.map((lecture) => {
        if (lecture.id === lectureId) {
          return {
            ...lecture,
            students: lecture.students.map((student) => {
              if (student.id === studentId) {
                return {
                  ...student,
                  [field]: gradeValue,
                };
              }
              return student;
            }),
          };
        }
        return lecture;
      });
    });
  };

  const handleSubmitGrades = async (lecture) => {
    const token = getToken();
    if (!token) {
      toast.error("Authentication required for saving grades.");
      return;
    }

    setSavingLectureId(lecture.id);

    try {
     
      const studentsWithGrades = lecture.students.filter(
        (student) =>
          (student.midterm !== null && student.midterm !== "") ||
          (student.finalGrade !== null && student.finalGrade !== "")
      );

      if (studentsWithGrades.length === 0) {
        toast.warn("No grades entered to save for this lecture.");
        setSavingLectureId(null);
        return;
      }

      
      const savePromises = studentsWithGrades.map(async (student) => {
        const gradePayload = {
          studentId: student.id.toString(),
          midterm: student.midterm ? student.midterm.toString() : null,
          finalGrade: student.finalGrade ? student.finalGrade.toString() : null,
          courseId: lecture.id.toString(),
        };

        const res = await fetch(
          `http://localhost:8080/api/teacher/save-grade`,
          {
            method: "PUT", 
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(gradePayload), 
          }
        );

        if (!res.ok) {
          const errorText = await res.text();
          let errorData = {};
          try {
            errorData = JSON.parse(errorText);
          } catch (e) {
            console.warn(
              "Backend save grades response is not JSON:",
              errorText
            );
          }
          const errorMessage =
            errorData?.exception?.message ||
            `Failed to save grade for student ${student.id}.`;
          throw new Error(errorMessage);
        }
        return { studentId: student.id, success: true };
      });

      
      await Promise.all(savePromises);
      toast.success("All grades saved successfully!");
    } catch (err) {
      toast.error(`Failed to save grades: ${err.message}`);
      console.error("Save grades error:", err);
    } finally {
      setSavingLectureId(null);
    }
  };

  const calculateLetterGrade = (midterm, finalGrade) => {
    const parsedMidterm =
      midterm == null || midterm === "" ? null : Number(midterm);
    const parsedFinalGrade =
      finalGrade == null || finalGrade === "" ? null : Number(finalGrade);

    if (
      parsedMidterm == null ||
      parsedFinalGrade == null ||
      isNaN(parsedMidterm) ||
      isNaN(parsedFinalGrade)
    )
      return "";

    const average = parsedMidterm * 0.4 + parsedFinalGrade * 0.6;
    if (average >= 90) return "AA";
    else if (average >= 85) return "BA";
    else if (average >= 80) return "BB";
    else if (average >= 75) return "CB";
    else if (average >= 70) return "CC";
    else if (average >= 65) return "DC";
    else if (average >= 60) return "DD";
    else if (average >= 50) return "FD";
    else return "FF";
  };

  if (loading) {
    return <div className={styles.loading}>Loading notes and lectures...</div>;
  }

  if (error) {
    return <div className={styles.error}>Error: {error}</div>;
  }

  return (
    <div className={styles.container}>
      <nav className={styles.sidebar}>
        <div className={styles.menuItem}>
          <a href="/academician/students">Students</a>
        </div>
        <div className={styles.menuItem}>
          <a href="#">Note Entry</a>
        </div>
        <div className={styles.menuItem}>
          <a href="/academician/user">User Information</a>
        </div>
      </nav>

      <div className={styles.main}>
        <div className={styles.header}>
          <a href="/academician" className={styles.homeIcon}>
            <i className="fas fa-home"></i>
          </a>
          Note Entry {teacher && ` - ${teacher.name} ${teacher.surname}`}
          <a href="/" className={styles.logout}>
            <i class="fa-solid fa-right-from-bracket"></i>
            Log out
          </a>
        </div>

        {lectures.length === 0 ? (
          <p className={styles.noDataMessage}>
            No lectures found or no students assigned to your lectures.
          </p>
        ) : (
          lectures.map((lecture) => (
            <div key={lecture.id} className={styles.middle}>
              <h2>
                Notes for {lecture.name} (Course ID: {lecture.id})
              </h2>
              {lecture.students && lecture.students.length > 0 ? (
                <table className="table table-bordered bg-white">
                  <thead className={styles.tableCustom}>
                    <tr>
                      <th>Student ID</th>
                      <th>Student Name & Surname</th>
                      <th>Midterm Grade</th>
                      <th>Final Grade</th>
                      <th>Letter Grade</th>
                    </tr>
                  </thead>
                  <tbody>
                    {lecture.students.map((student) => (
                      <tr key={student.id}>
                        <td>{student.id}</td>
                        <td>
                          {student.name} {student.surname}
                        </td>
                        <td>
                          <input
                            type="number"
                            min="0"
                            max="100"
                            value={student.midterm ?? ""}
                            onChange={(e) =>
                              handleGradeChange(
                                lecture.id,
                                student.id,
                                "midterm",
                                e.target.value
                              )
                            }
                          />
                        </td>
                        <td>
                          <input
                            type="number"
                            min="0"
                            max="100"
                            value={student.finalGrade ?? ""}
                            onChange={(e) =>
                              handleGradeChange(
                                lecture.id,
                                student.id,
                                "finalGrade",
                                e.target.value
                              )
                            }
                          />
                        </td>
                        <td>
                          {calculateLetterGrade(
                            student.midterm,
                            student.finalGrade
                          )}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              ) : (
                <p className={styles.noDataMessage}>
                  No students enrolled in this lecture yet.
                </p>
              )}
              <button
                className={styles.Button}
                onClick={() => handleSubmitGrades(lecture)}
                disabled={savingLectureId === lecture.id}
              >
                {savingLectureId === lecture.id ? "Saving..." : "Save Grades"}
              </button>
            </div>
          ))
        )}
      </div>
      <ToastContainer position="top-right" autoClose={2000} />
    </div>
  );
};

export default NoteEntry;
