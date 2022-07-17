package com.onlinemedicineshop.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 3244448812352887437L;
	private String email;
	private String password;
	private List<SimpleGrantedAuthority> roles;
	

	public UserDetailsImpl(User user, List<SimpleGrantedAuthority> roles) {
		
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = roles;
		//TODO Delete Below
		System.out.println("username " + email + " password - " + password + " roles - "
				+ roles.stream().map(p -> p.getAuthority()).collect(Collectors.toList()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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
