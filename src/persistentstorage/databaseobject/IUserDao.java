package persistentstorage.databaseobject;

import shared.definitions.CatanColor;

public interface IUserDao {
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @pre username and password are valid
	 * @post the user gets added to the database
	 */
	public void addUser(String username, String password);
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return 
	 * @pre username and password are valid
	 * @post returns true if the user is valid, false if user isn't
	 */
	public boolean validateUser(String username, String password);
	
	/**
	 * 
	 * @param gameId
	 * @param username
	 * @param playerColor
	 * @pre gameId, username, and playerColor are all valid
	 * @post a new player gets added the game
	 */
	public void addPlayer(int gameId, String username, CatanColor playerColor);
	
	/**
	 * 
	 * @param gameId
	 * @param username
	 * @param playerColor
	 * @return
	 * @pre gameId, username, and playerColor are valid 
	 * @post returns true if the user exists in the list of users, false if user doesn't exist
	 */
	public boolean isPlayer(int gameId, String username, CatanColor playerColor);
	
	/**
	 * 
	 * @param gameId
	 * @param username
	 * @return
	 * @pre gameId and username are valid 
	 * @post returns a the player's color from the given game
	 */
	public CatanColor getColor(int gameId, String username); 
	
	


}
