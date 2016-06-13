package persistentstorage;

import persistentstorage.databaseobject.IGameDAO;
import persistentstorage.databaseobject.IUserDao;

public interface IDAOFactory {
	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	public abstract IUserDao getUserDAO();

	/**
	 * @pre none
	 * @post the gameDAO is returned
	 */
	public abstract IGameDAO getGameDAO();

}
