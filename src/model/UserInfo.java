package model;

public class UserInfo {

	private String username;
	private String password;
	private Integer playerID; 
	
	public UserInfo(String u, String p, Integer i){
		username = u;
		password = p;
		playerID = i;
		
	}

	public Integer getPlayerID() {
		return playerID;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
