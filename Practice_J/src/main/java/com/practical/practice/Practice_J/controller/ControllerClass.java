package com.practical.practice.Practice_J.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.practical.practice.Practice_J.Models.AuthenticationRequest;
import com.practical.practice.Practice_J.Models.AuthenticationResponse;
import com.practical.practice.Practice_J.Util.JwtUtil;
import com.practical.practice.Practice_J.security.MyUserDetailsService;

@RestController
public class ControllerClass {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@RequestMapping("/hello")
	public String getMessage() {
		return "Hello Message";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> tokenAuthentication(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
try{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword())

		);
}
catch(BadCredentialsException e){
	throw new Exception ("username and password are incorrect");
}
	UserDetails userDetails=myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	String jwt=jwtUtil.generateToken(userDetails);
 return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
