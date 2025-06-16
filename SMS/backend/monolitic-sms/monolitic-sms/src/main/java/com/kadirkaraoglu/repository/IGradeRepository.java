package com.kadirkaraoglu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.Grade;
@Repository
public interface IGradeRepository extends JpaRepository<Grade, Long>{

}
