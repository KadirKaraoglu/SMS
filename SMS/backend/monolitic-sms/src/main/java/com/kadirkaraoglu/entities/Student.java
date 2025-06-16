package com.kadirkaraoglu.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student extends Person {
	@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_courses", 
               joinColumns = @JoinColumn(name = "student_id"), 
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<Course>();

    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;

    @ManyToOne
    private Faculty faculty;
}
