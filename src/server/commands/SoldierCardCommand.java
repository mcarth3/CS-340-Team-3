package server.commands;

import server.ICommand;

public class SoldierCardCommand implements ICommand {

	/**
	 * soldier card is played for player
	 * 
	 * @param data
	 *            the soldierJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: soldier card is removed and its effects are added to the model
	 */
	@Override
	public Object execute(Object data) {
		return data;
		// TODO Auto-generated method stub

	}
}
