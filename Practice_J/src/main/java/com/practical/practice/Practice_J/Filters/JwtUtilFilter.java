package com.practical.practice.Practice_J.Filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practical.practice.Practice_J.Util.JwtUtil;
import com.practical.practice.Practice_J.security.MyUserDetailsService;

@Service
public class JwtUtilFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	 final String authorizationHeader=request.getHeader("Authorization");
	 String username=null;
	 String jwt=null;
	 
	 if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
		 jwt=authorizationHeader.substring(7);
		 username=jwtUtil.extractUsername(jwt);
	 }
	 if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){
		 UserDetails userDetails=myUserDetailsService.loadUserByUsername(username);
	 
	 if(jwtUtil.validateToken(jwt, userDetails)){
		 UsernamePasswordAuthenticationToken authenticatedToken=
				 		new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		 authenticatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		 SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
	 }
	
	 }
		filterChain.doFilter(request,response);
	 }


}
