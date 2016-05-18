package client.login;

public class PlayerLoginInfo {
	
	public String name;
	public String password;
	public Integer playerID;

	public PlayerLoginInfo(){}
	public PlayerLoginInfo(String n, String p, Integer pid){
		name = n;
		password = p; 
		playerID = pid;
	}
	
}
