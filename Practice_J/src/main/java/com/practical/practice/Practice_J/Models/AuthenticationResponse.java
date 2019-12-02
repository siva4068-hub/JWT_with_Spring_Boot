package com.practical.practice.Practice_J.Models;

public class AuthenticationResponse {
private final String jwt;
public AuthenticationResponse(String jwt){
	this.jwt=jwt;
}
/*public String getJwt(){
	return jwt;
}
*/
public String getJwt() {
	return jwt;
}

}
