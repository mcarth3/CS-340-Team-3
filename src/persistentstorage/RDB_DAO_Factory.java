package persistentstorage;

import persistentstorage.databaseobject.IGameDAO;
import persistentstorage.databaseobject.IUserDao;

public class RDB_DAO_Factory implements IDAOFactory {
	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	@Override
	public IUserDao getUserDAO() {
		return null;
	}

	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	@Override
	public IGameDAO getGameDAO() {
		return null;
	}

}
