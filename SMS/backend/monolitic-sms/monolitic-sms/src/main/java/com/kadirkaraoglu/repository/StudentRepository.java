package com.kadirkaraoglu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

		public Student findStudentByName(String firstName);
		public Student findStudentByUsername(String personId);
		public Student findStudentByTcNo(String tckn);
}
