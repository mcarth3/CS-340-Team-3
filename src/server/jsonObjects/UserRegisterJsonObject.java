package server.jsonObjects;

public class UserRegisterJsonObject {

	private String username;
	private String password; 
	
	public UserRegisterJsonObject(String u, String p){
		username = u; 
		password = p; 
	}
	public String getUsername(){
		return username; 
	}
	public String getPassword(){
		return password; 
	}
}
