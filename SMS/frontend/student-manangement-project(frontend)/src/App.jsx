import React from "react";
import { Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage";
import Login from "./pages/LoginPage";
import StudentPage from "./pages/StudentDashboard";
import ClassesStudent from "./pages/ClassesStudent";
import NotesStudent from  "./pages/NotesStudent"
import UserInformationStudent from "./pages/UserInformationStudent"
import AdminPage from "./pages/AdminDashboard";
import ManageStudent from './pages/ManageStudent';
import CoursesStudent from './pages/CoursesStudent';
import ManageTeacher from "./pages/ManageTeacher";
import AcademicianDashboard from "./pages/AcademicianDashboard";
import UserInformationTeacher from "./pages/UserInformationTeacher"
import NoteEntry from "./pages/NoteEntry"
import ChangePassword from "./pages/ChangePassword";

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login/:role" element={<Login />} />
      <Route path="/student" element={<StudentPage />} />
      <Route path="/student/classes" element={<ClassesStudent/>} />
      <Route path="/student/notes" element={<NotesStudent/>} />
      <Route path="/student/user" element={<UserInformationStudent/>} />
      <Route path="/admin" element={<AdminPage />} />
      <Route path="/admin/student/ManageStudent" element={<ManageStudent/>} />
      <Route path="/admin/student/CoursesStudent" element={<CoursesStudent/>} />
      <Route path="/admin/teacher/ManageTeacher" element={<ManageTeacher/>} />
      <Route path="/academician" element={<AcademicianDashboard />} />
      <Route path="/academician/user" element={<UserInformationTeacher/>} />
      <Route path="/academician/notes" element={<NoteEntry/>} />
      <Route path="/changePassword" element={<ChangePassword />} />

    </Routes>
  );
};

export default App;
