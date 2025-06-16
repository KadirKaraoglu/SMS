package com.kadirkaraoglu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkaraoglu.entities.Course;
@Repository
public interface ICourseRepository extends JpaRepository<Course, Long>{

}
