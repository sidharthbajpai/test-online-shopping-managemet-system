package com.onlinemedicineshop.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinemedicineshop.security.model.User;
import com.onlinemedicineshop.security.model.UserDetailsImpl;
import com.onlinemedicineshop.service.AdminService;
import com.onlinemedicineshop.service.CustomerService;
import com.onlinemedicineshop.util.JwtUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {
		//Change this below to databse query
		String email = jwtUtil.extractEmail(jwt);
//		if(!(username.equals("user") || username.equals("admin"))) {
//			throw new RuntimeException("username incorrect");
//		}
//		AuthenticationRequest user = new AuthenticationRequest();
//		user.setUsername(username);
		List<SimpleGrantedAuthority> roles = jwtUtil.extractRoles(jwt); 
				//can be delted below i think
		boolean isAdmin = roles.stream().map(p -> p.getAuthority()).anyMatch(p -> p.equals("ROLE_ADMIN"));
		boolean isUser = roles.stream().map(p -> p.getAuthority()).anyMatch(p -> p.equals("ROLE_USER"));
		Optional<User> user = null;
		//TODO throw error here
		if(isAdmin) { // load admin from databse
			user = adminService.getAdminAsUserByEmail(email);		
		}
		if(isUser) {
//			user.setPassword("$2a$12$QwS5szlzNdnwmhvMPKz6Z.9go2OzeLWwUNLrxeeU4kjpbUgSW87UC");
			user = customerService.getCustomerAsUserByEmail(email);
		}
		user.orElseThrow(() -> new RuntimeException("user not found"));
		return new UserDetailsImpl(user.get(), roles);
	}

}
