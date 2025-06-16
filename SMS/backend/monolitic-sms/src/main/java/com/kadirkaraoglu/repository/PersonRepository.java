package com.kadirkaraoglu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kadirkaraoglu.entities.Person;
@Repository
public interface PersonRepository extends JpaRepository<Person , Long>{
	public Person findPersonByUsername(String username);
	 boolean existsByUsername(String username);

}