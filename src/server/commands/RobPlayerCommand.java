package server.commands;

import server.ICommand;

public class RobPlayerCommand implements ICommand {

	/**
	 * robbing actions are done
	 *
	 * @param data
	 *            the robJsonObject containing this method's needed info
	 * @pre: ServerModel is initialized and HTTP request is decoded
	 * @post: a player is robbed, the robber is moved
	 */

	@Override
	public Object execute(Object data) {
		return data;
		// TODO Auto-generated method stub

	}
}
