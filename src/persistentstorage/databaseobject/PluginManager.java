package persistentstorage.databaseobject;

import java.util.ArrayList;
import java.util.Map;

import server.ICommand;

public class PluginManager {
	private static ArrayList<ICommand> commandList = new ArrayList<ICommand>();

	private IGameDao gameDAO;
	private IUserDao userDAO;
	private Map<String, String> FactoryPluginMap;

	/**
	 * Takes a commandline argument as a string and sets the game and user DAOs to the associated string given via the map
	 * @param Plugintype - the arguments ran with the program
	 * @pre Plugintype is either "FlatFile" or "RDB"
	 * @post the gameDAO and userDAO are set from the appropriate plugin
	 */
	public void setDAOs(String Plugintype) {
		//IDAOFactory gotten from FactoryPluginMap
		//the getuserdao and getgamedao commands are called on the IDAOFactory and the responses are set to the game and user daos
	}

	/**
	 * gameDAO getter
	 * @pre none
	 * @post the gameDAO is returned
	 */
	public IGameDao getgameDAO() {
		return gameDAO;
	}

	/**
	 * userDAO getter
	 * @pre none
	 * @post the userDAO is returned
	 */
	public IUserDao getuserDAO() {
		return userDAO;
	}

}
