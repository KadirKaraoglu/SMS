package com.kadirkaraoglu.entities;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
	ROLE_ADMIN("ADMIN"),
	ROLE_STUDENT("STUDENT"),
	ROLE_TEACHER("TEACHER");
	 
	private String value;

	private RoleType(String value) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}
	
	

}
