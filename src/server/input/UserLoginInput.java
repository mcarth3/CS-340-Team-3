package server.input;

public class UserLoginInput {

	private String username;
	private String password; 
	
	public UserLoginInput(String u, String p){
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
