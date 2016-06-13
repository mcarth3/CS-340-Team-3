package persistentstorage;

import persistentstorage.databaseobject.IGameDao;
import persistentstorage.databaseobject.IUserDao;

public interface IDAOFactory {
	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	public IUserDao getUserDAO();

	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	public IGameDao getGameDAO();

}
