package com.kadirkaraoglu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.entities.Teacher;
@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, Long>{
	public Optional<Teacher> findTeacherByTcNo(String tckn);
	public Teacher findTeacherByUsername(String username);

}
