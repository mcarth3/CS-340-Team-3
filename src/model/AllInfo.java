package model;

import client.data.GameInfo;

public class AllInfo {

	private UserInfo[] users;
	private Game[] games;
	private GameInfo[] gameList;
	
	
	public AllInfo(UserInfo[] u, Game[] g, GameInfo[] l){
		users = u;
		games = g; 
		gameList = l;
	}
	
	public GameInfo[] getGameList(){
		return gameList; 
	}
	public UserInfo[] getUsers() {
		return users;
	}
	public void setUsers(UserInfo[] users) {
		this.users = users;
	}
	public Game[] getGames() {
		return games;
	}
	public void setGames(Game[] games) {
		this.games = games;
	}	
}
