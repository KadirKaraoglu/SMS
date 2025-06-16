	package com.kadirkaraoglu.entities;
	
	import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	@Data
	@AllArgsConstructor 
	@NoArgsConstructor
	@MappedSuperclass
	public abstract class Person {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(unique = false)
	    private boolean isFirstLogin = true;
	    private String username;
	    @Column( )
	    private String name;
	    @Column( )
	    private String surname;
	    @Column (unique = true )
	    private String tcNo;
	    @Column( )
	    private LocalDate birthDate;
	    @Enumerated(EnumType.STRING)
	    private RoleType role;
	    @Column()
	    private String password;
	
	}
