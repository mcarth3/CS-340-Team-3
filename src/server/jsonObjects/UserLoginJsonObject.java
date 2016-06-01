package server.jsonObjects;

public class UserLoginJsonObject {

	private String username;
	private String password; 
	
	public UserLoginJsonObject(String u, String p){
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
