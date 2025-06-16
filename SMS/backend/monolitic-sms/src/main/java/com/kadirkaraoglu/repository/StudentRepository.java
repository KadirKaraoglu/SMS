package com.kadirkaraoglu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

		public Student findStudentByName(String firstName);
		public Student findStudentByUsername(String username);
		public Optional<Student> findStudentByTcNo(String tckn);
		@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s JOIN s.courses c WHERE s.id = :studentId AND c.courseId = :courseId")
		boolean isStudentEnrolledInCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

}
