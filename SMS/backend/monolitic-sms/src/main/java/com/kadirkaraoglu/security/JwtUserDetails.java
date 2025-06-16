package com.kadirkaraoglu.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kadirkaraoglu.entities.Person;
import com.kadirkaraoglu.entities.RoleType;

import lombok.Data;
@Data
public class JwtUserDetails implements UserDetails {
	public Long id ; 
	private String username;
	private String password;
	private RoleType role ;

	 public JwtUserDetails(Long id,  String password,String username,
             RoleType role) {
		 this.id = id;
		 this.username  = username;
		 this.password = password;
		 this.role = role;
}
	
	  public static JwtUserDetails create(Person person) {
	      
	        return new JwtUserDetails(
	        		person.getId(),
	        		person.getPassword(),
	        		person.getUsername(),
	                person.getRole()
	        );
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(role);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	  @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	

}
