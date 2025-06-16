	package com.kadirkaraoglu.entities;
	
	import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	
	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Table(name = "course")
	public class Course {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long courseId;
	
	    @Column
	    private String courseName;
	
	    @Column(unique = true)
	    private String courseCode;
	
	    @Column
	    private Long quota;
	
	    @Column
	    private int sumOfCurrentStudent;
	
	    @Column
	    private int credit;
	
	    @Column
	    private int akts;
	    @JsonIgnore
	    @ManyToMany(mappedBy = "courses")
	    private Set<Student> students = new HashSet<Student>();
	
	    @ManyToOne
	    private Teacher teacher;
	
	    @OneToMany(mappedBy = "course")
	    private Set<Grade> grades;
	    
	    //nedenını tama anlamadım ama anladıgım kadrıyla kontrol yaparken bunlar override etmen gerekıyomus yoksa hibernatete sıkıntı olurmus
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Course)) return false;
	        Course course = (Course) o;
	        return Objects.equals(courseId, course.courseId);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(courseId);
	    }
	}

