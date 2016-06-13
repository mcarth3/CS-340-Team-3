package persistentstorage;

import persistentstorage.databaseobject.IGameDAO;
import persistentstorage.databaseobject.IUserDao;

public class FlatFile_DAO_Factory implements IDAOFactory {
	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	@Override
	public IUserDao getUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	@Override
	public IGameDAO getGameDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
