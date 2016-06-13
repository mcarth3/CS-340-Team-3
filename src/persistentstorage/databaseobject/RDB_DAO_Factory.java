package persistentstorage.databaseobject;

import persistentstorage.IDAOFactory;

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
	public IGameDao getGameDAO() {
		return null;
	}

}
