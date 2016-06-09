package persistentstorage.databaseobject;

import shared.definitions.CatanColor;

public class RDBUserDao implements IUserDao {

	@Override
	public void addUser(String UserName, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addPlayer(int gameId, String username, CatanColor playerColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPlayer(int gameId, String username, CatanColor playerColor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CatanColor getColor(int gameId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
