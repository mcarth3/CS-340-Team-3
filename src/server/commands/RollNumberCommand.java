package server.commands;

import server.ICommand;
import server.ServerFacade;
import server.jsonObjects.RollJsonObject;

public class RollNumberCommand implements ICommand {
	private RollJsonObject rollingobject;

	/**
	 * dice roll actions are done
	 *
	 * @param data the rollJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: players recieve resources if they're on the number that was rolled, status is changed to playing
	 */

	@Override
	public Object execute(Object data) {
		rollingobject = (RollJsonObject) data;
		return ServerFacade.getSingleton().rolldice();

	}
}
