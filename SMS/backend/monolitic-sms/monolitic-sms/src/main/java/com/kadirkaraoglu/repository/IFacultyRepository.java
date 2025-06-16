package com.kadirkaraoglu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkaraoglu.entities.Faculty;
@Repository
public interface IFacultyRepository extends JpaRepository<Faculty, Long> {

}
